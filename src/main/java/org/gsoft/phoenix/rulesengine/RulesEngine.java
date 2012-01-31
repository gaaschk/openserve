package org.gsoft.phoenix.rulesengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.Predicate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gsoft.phoenix.buslogic.system.SystemSettingsLogic;
import org.gsoft.phoenix.domain.PhoenixDomainObject;
import org.gsoft.phoenix.util.ApplicationContextLocator;
import org.gsoft.phoenix.util.ReversibleMultiMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope( value = "thread" )
public class RulesEngine {
    private static final Logger LOG = LogManager.getLogger( "ruleTrace" );
    
    @Resource
    private SystemSettingsLogic systemSettingsLogic;
    @Resource
    private ApplicationContext applicationContext;
    //Put this in here to guarantee that the ApplicationContextLocator is
    //initialized before any rules/facts get executed.
    @Resource
    private ApplicationContextLocator applicationContextLocator;
    
    /**
     * A mapping of classes to objects in the context. This allows us to collect all the context objects that are of a
     * given type.
     */
    final ReversibleMultiMap<Class<? extends PhoenixDomainObject>, PhoenixDomainObject> contextObjectByType;
    
    /**
     * A mapping of context object classes to the fact expression definitions that need them. For example, if the
     * (hypothetical) ManipulateActiveLoan rule's expression definition has an expectation for the LoanIsActive fact and
     * the fact requires a Loan, the mapping would be from the Loan.class to the expression definition object. This
     * allows us to find all fact expression definitions a context object can impact.
     */
    final ReversibleMultiMap<Class<? extends PhoenixDomainObject>, ComplexFactExpressionDefinition> expressionDefinitionsByContextType;
    
    /**
     * A mapping of context object instances to the fact instances that use them. This allows us to find all the facts
     * that are using a given context object and (since it is reversible) find all the context objects that a fact is
     * using.
     */
    final ReversibleMultiMap<PhoenixDomainObject, Fact> factsByContext;
    
    /**
     * A prioritized queue of facts we're planning to evaluate, priority sorts them into the order we expect to evaluate
     * them. The facts contained in the queue are compared using a {@link FactPriorityComparator}.
     */
    final PriorityQueue<Fact> factsToEval;
    
    /**
     * A mapping of context object classes to the fact classes that need them. For example, if the (hypothetical)
     * LoanIsActive fact requires a Loan, the mapping would be from the LoanIsActive.class to the Loan.class. This
     * allows us to find all facts a context object can impact and (since it is reversible) find what context objects a
     * fact requires.
     */
    final ReversibleMultiMap<Class<? extends PhoenixDomainObject>, Class<? extends Fact>> factTypesByContextType;
    
    /**
     * A collection of all the rules in the system. Note that rules are found through the BeanFactory. Any class marked
     * with the Component annotation that is also an implementor of the Rule interface, will be plugged in here
     * automatically.
     */
    Collection<Rule> rules;
    
    /**
     * A mapping of fact classes to the rules that need them. For example, if the (hypothetical) ManipulateActiveLoan
     * rule, requires the LoanIsActive fact, the mapping would be from LoanIsActive.class to the the
     * ManipulateActiveLoan instance. This allows us to find all rules a fact can impact and (since it is reversible)
     * find all the fact types a rule needs.
     */
    final ReversibleMultiMap<Class<? extends Fact>, Rule> rulesByFactType;
    
    /**
     * A map of old context objects to their current version.
     */
    private final Map<PhoenixDomainObject, PhoenixDomainObject> contextObjectVersionMap;
    
    /**
     * A map of expression definitions to "instances" of the definition. Although implemented as a ReversibleMultiMap,
     * this is only intended to be used as a MultiMap. That is, for each definition, there may be multiple expressions,
     * but for each expression there should only be one definition. Also, the definition for an expression should be
     * found by asking the expression, not by attempting a reverse-lookup in this map.
     */
    private final ReversibleMultiMap<ComplexFactExpressionDefinition, ComplexFactExpression> factExpressionByDefinition;
    
    private boolean initialized;
    
    private boolean isRunning = false;
    private boolean isOpen = false;
    
    /**
     * Creates a new rules engine instance with no context.
     */
    public RulesEngine() {
        this.contextObjectByType = new ReversibleMultiMap<Class<? extends PhoenixDomainObject>, PhoenixDomainObject>();
        this.factTypesByContextType =
                new ReversibleMultiMap<Class<? extends PhoenixDomainObject>, Class<? extends Fact>>();
        this.rulesByFactType = new ReversibleMultiMap<Class<? extends Fact>, Rule>();
        this.factsByContext = new ReversibleMultiMap<PhoenixDomainObject, Fact>();
        this.factsToEval = new PriorityQueue<Fact>( 11, new FactPriorityComparator() );
        this.contextObjectVersionMap = new HashMap<PhoenixDomainObject, PhoenixDomainObject>();
        this.factExpressionByDefinition =
                new ReversibleMultiMap<ComplexFactExpressionDefinition, ComplexFactExpression>();
        this.rules = new ArrayList<Rule>();
        this.expressionDefinitionsByContextType =
                new ReversibleMultiMap<Class<? extends PhoenixDomainObject>, ComplexFactExpressionDefinition>();
    }
    
    /**
     * Adds one or more context objects to the engine's knowledge base. This method effectively kicks off the
     * RulesEngine, but is designed to be re-entrant. That is, the first call to this method when the context was empty,
     * will start the fact-evaluation/rule-firing process. As part of that process, it is perfectly reasonable for
     * additional context to be added. That additional context will be noted and additional facts/rules will be
     * evaluated/fired accordingly. <b>IMPORTANT:</b> This should be used only for adding <i>new</i> context objects, if
     * this is a new version of an object that is already in the context, the
     * {@link #replaceContext(PhoenixDomainObject, PhoenixDomainObject)} method should be used. This method will ignore
     * objects that are already in the context, but it is not capable of determining that in certain cases (if {Object
     * {@link #equals(Object)} is not implemented properly).
     * 
     * @param addedContext
     *            One or more domain objects to be added to the context.
     */
    public void addContext( final PhoenixDomainObject... addedContext ) {
        if(!this.isOpen){
        	RulesEngine.LOG.trace("call to addContext made while rules engine is closed.  objects ignored.");
        	return;
        }
    	// null objects should never be passed to addContext()
        if ( addedContext == null ) {
            throw new IllegalArgumentException( "Null object passed into context." );
        }
        
        // initialize the BRE if needed
        this.initialize();
        
        // gather any new rules & facts that are relevant to the added context
        this.incorporateNewContextObjects( addedContext );
    }
    
    public void evaluateRules(){
    	try{
    		if(!this.isRunning){
    			isRunning = true;
    			this.addUniversalContext();
    			boolean shouldContinue = true;
    			while ( shouldContinue ) {
    				shouldContinue = this.evaluateFactsAndFireRules();
    				RulesEngine.LOG.trace( "--- Resetting Eval Queue ---" );
    				this.resetEvalQueue();
    			}
    		}
    	}
    	finally{
    		this.isRunning = false;
    	}
   }
    
    public void forceStop(){
    	this.isRunning = false;
    	this.clearContext();
    }
    
    public void open(){
    	this.isOpen = true;
    }
    
    public void close(){
    	this.clearContext();
    	this.isOpen = false;
    }
    
	public boolean isOpen() {
		return this.isOpen;
	}

	/**
     * replaces old context if it already exists, otherwise adds the new object to the BRE context
     * 
     * @param old
     * @param replacement
     */
    public void addOrReplaceContext( final PhoenixDomainObject old, final PhoenixDomainObject replacement ) {
        if ( !this.replaceContext( old, replacement ) ) {
            this.addContext( replacement );
        }
    }
    
    public void clearContext() {
        if ( !this.contextObjectByType.isEmpty() ) {
            RulesEngine.LOG.trace( "\n*** Rules Engine - Clear Context ***\n" );
        }
        this.contextObjectByType.clear();
        this.factsToEval.clear();
        this.factsByContext.clear();
        this.contextObjectVersionMap.clear();
        this.factExpressionByDefinition.clear();
        if(this.isRunning){
        	this.addUniversalContext();
        }
    }
    
    /**
     * Finds an object in the context that matches both the type (contextClass) and the predicate passed-in. If more
     * than one match exists, only the first one found is returned. Ordering is not specified and should not be relied
     * upon. Note that the predicate is only run against the latest versions of the objects in the context.
     * 
     * @param <T>
     *            The type of the object to return.
     * @param contextClass
     *            The class object of the type of object to return.
     * @param predicate
     *            A method to evaluate against objects of the right type in the context.
     * @return A single match or null if none can be found.
     */
    public <T extends PhoenixDomainObject> T findInContext( final Class<T> contextClass, final Predicate predicate ) {
        final Collection<T> objectsWithSameType = this.getContextObjectsOfType( contextClass );
        if ( objectsWithSameType != null ) {
            for ( final T potentialMatch : objectsWithSameType ) {
                if ( predicate.evaluate( potentialMatch ) ) {
                    return potentialMatch;
                }
            }
        }
        return null;
    }
    
    /**
     * Finds the most recent version of the passed-in context object.
     * 
     * @param potentialMatch
     *            The context object for which to search.
     * @return The most recent version of the context object or <code>null</code> if the object is not in the context.
     */
    @SuppressWarnings("unchecked")
	public <T extends PhoenixDomainObject> T findInContext( final T potentialMatch ) {
        // If what was passed in is the latest of the new versions, just return it.
        if ( this.contextObjectVersionMap.containsValue( potentialMatch ) ) {
            return (T)this.contextObjectVersionMap.get( potentialMatch );
        }
        
        // Otherwise, find the newest version.
        final T newVersion = (T)this.contextObjectVersionMap.get( potentialMatch );
        if ( newVersion == null ) {
            // If there's nothing new, check if it is the original version and should be returned.
            if ( this.contextObjectByType.containsValue( potentialMatch ) ) {
                return potentialMatch;
            }
            else {
                RulesEngine.LOG.trace( "Looking for context object and found null. Searched for: " +
                        potentialMatch.getClass().getSimpleName() + "[" + potentialMatch.getID() + "]" );
            }
        }
        // Finally, return the newVersion (which might be null).
        return newVersion;
    }
    
    /**
     * Finds all of the context objects of the provided type.
     */
    @SuppressWarnings( "unchecked" )
    public <T extends PhoenixDomainObject> Collection<T> getContextObjectsOfType( final Class<T> type ) {
        final Collection<T> objectsWithSameType = (Collection<T>)this.contextObjectByType.get( type );
        final List<T> returnList = new ArrayList<T>();
        if ( ( objectsWithSameType != null ) && !objectsWithSameType.isEmpty() ) {
            for ( final PhoenixDomainObject obj : objectsWithSameType ) {
                returnList.add( (T)this.findInContext( obj ) );
            }
        }
        return returnList;
    }
    
    public ReversibleMultiMap<PhoenixDomainObject, Fact> getFactsByContext() {
        return this.factsByContext;
    }
    
    /**
     * Replaces a context object with a new version. If the old object was not already in the context, it is ignored
     * without throwing an exception. This is meant to allow for attempts to replace when the calling code does not care
     * whether the object is in the context or not. This will not "kick off" fact/rule evaluation. This method is
     * required because in the system as designed, it is possible (likely) that a call to a DAO's save method will
     * result in the creation of a new instance. To make things worse, the old instance may be still around somewhere.
     * By calling this method from the save (should be via AOP), this RulesEngine will always return the most current
     * copy of the object when asked. (E.G. {@link #findInContext(PhoenixDomainObject)}) Note that to make this certain
     * to work (given current equals() implementations), all of the old versions are kept around.
     * 
     * @param old
     *            The domain object to be replaced.
     * @param replacement
     *            The new version of the object.
     */
    public boolean replaceContext( final PhoenixDomainObject old, final PhoenixDomainObject replacement ) {
        // null objects should never be passed to addContext()
        if ( old == null ) {
            throw new IllegalArgumentException( "Null object passed into context." );
        }
        if ( replacement == null ) {
            throw new IllegalArgumentException( "Null object passed into context." );
        }
        
        RulesEngine.LOG.trace( "RulesEngine replacing context object: Old = " + old.getClass().getSimpleName() + "[" +
                old.getID() + "]" + "| Replacement = " + replacement.getClass().getSimpleName() + "[" +
                replacement.getID() + "]" );
        boolean replaced = false;
        if ( old == replacement ) {
            RulesEngine.LOG.trace( "  excluding as duplicate." );
            return replaced;
        }
        final boolean oldIsLatestVersion =
                this.contextObjectVersionMap.containsValue( old ) || this.contextObjectByType.containsValue( old );
        final boolean oldWasPreviousVersion = this.contextObjectVersionMap.containsKey( old );
        RulesEngine.LOG
                .trace( "  olsIsLatest = " + oldIsLatestVersion + " : oldWasPrevious = " + oldWasPreviousVersion );
        if ( oldIsLatestVersion ) {
            this.replaceContextObjectVersionValue( old, replacement );
            this.contextObjectVersionMap.put( replacement, replacement );
            // we need to maintain our fact/context mapping with the most current context
            this.updateFactsByContext( old, replacement );
            replaced = true;
        }
        else if ( oldWasPreviousVersion ) {
            final PhoenixDomainObject currentValue = this.contextObjectVersionMap.get( old );
            this.replaceContextObjectVersionValue( currentValue, replacement );
            // we need to maintain our fact/context mapping with the most current context
            this.updateFactsByContext( old, replacement );
            replaced = true;
        }
        return replaced;
    }
    
    /**
     * Primarily for use by unit tests, this method adds the passed-in rule and all its required facts to the knowledge
     * base of the rules engine.
     */
    void addExternalRule( final Rule rule ) {
        this.rules.add( rule );
        this.mapRule( rule );
    }
    
    /**
     * Intended for internal use only (can also be called by unit tests), this method adds the passed-in fact type to
     * the knowledge base of the rules engine. If the fact type was not installed normally, no rules depend on it, and
     * it will never be evaluated.
     */
    void addFactType( final Class<? extends Fact> factClass ) {
        final Class<? extends PhoenixDomainObject>[] params = FactUtil.determineConstructorTypesForFactType( factClass );
        if ( !this.factTypesByContextType.containsValue( factClass ) ) {
            for ( final Class<? extends PhoenixDomainObject> contextNeed : params ) {
                this.factTypesByContextType.put( contextNeed, factClass );
            }
        }
    }
    
    /**
     * This method incorporates a new context object into the maps and figures out what new facts can be evaluated given
     * the new information. It also tries to ignore duplicates. (It "tries" as this implementation is only as good as
     * the equals/hashcode implementation for the domain object.)
     */
    void incorporateNewContextObject( final PhoenixDomainObject addedObject ) {
        RulesEngine.LOG.trace( "RulesEngine incorporating context object: " + addedObject.getClass().getSimpleName() +
                "[" + addedObject.getID() + "]" );
        
        if ( this.contextObjectByType.containsValue( addedObject ) ||
                this.contextObjectVersionMap.containsValue( addedObject ) ||
                this.contextObjectVersionMap.containsKey( addedObject ) ) {
            RulesEngine.LOG.trace( "  excluding as a duplicate." );
            return;
        }
        
        // find all fact expression definitions which depend on the context
        // object
        final Collection<ComplexFactExpressionDefinition> expressionDefinitions =
                this.expressionDefinitionsByContextType.get( addedObject.getClass() );
        if ( expressionDefinitions != null ) {
            // for each one, determine if an expression or expressions can be
            // created -- if so, create them
            for ( final ComplexFactExpressionDefinition expressionDefinition : expressionDefinitions ) {
                final Set<Class<? extends PhoenixDomainObject>> contextTypes =
                        expressionDefinition.getAllContextTypeNeeds();
                final List<List<PhoenixDomainObject>> contextCombinations =
                        this.calculateContextCombinations( addedObject, contextTypes );
                for ( final List<PhoenixDomainObject> contextList : contextCombinations ) {
                    final ComplexFactExpression expression = new ComplexFactExpression( expressionDefinition );
                    expression.gatherFacts( contextList, this );
                    this.factExpressionByDefinition.put( expressionDefinition, expression );
                }
            }
        }
        // add the new context
        this.contextObjectByType.put( addedObject.getClass(), addedObject );
    }
    
    /**
     * Intended for internal use (also used by unit tests), this method causes the passed-in domain objects to be added
     * to the context of the rules engine.
     */
    void incorporateNewContextObjects( final PhoenixDomainObject[] addedContext ) {
        // iterate over context objects
        for ( final PhoenixDomainObject addedObject : addedContext ) {
            this.incorporateNewContextObject( addedObject );
        }
    }
    
    /**
     * Takes the new fact, adds it to the list of facts to be evaluated, and maps the fact to the context objects
     * provided. The context objects should be the parameters used to construct the fact. Used by
     * {@link FactExpression#createNeededFacts(List, RulesEngine)}.
     */
    void indexNewFact( final Fact newFact, final PhoenixDomainObject[] constructorParams ) {
        for ( final PhoenixDomainObject contextObj : constructorParams ) {
            this.factsByContext.put( contextObj, newFact );
        }
    }
    
    /**
     * Adjusts the position of the fact in the evaluation queue based on its priority, adds the fact appropriately if it
     * was not in the queue, or discards the fact if it cannot affect any expressions. For facts already in the queue,
     * this removes it from the queue and re-adds it if necessary. This method is useful for example, when a new fact
     * expression is found that is interested in the fact. Used by
     * {@link FactExpression#createNeededFacts(List, RulesEngine)},
     * {@link FactExpression#gatherExistingFactsForContext(List, ReversibleMultiMap)} , and
     * {@link #removeExpressionFromEvalQueue(FactExpression)}.
     */
    void prioritizeFactInEvalQueue( final Fact fact ) {
        final Iterator<Fact> queueIterator = this.factsToEval.iterator();
        while ( queueIterator.hasNext() ) {
            final Fact queuedFact = queueIterator.next();
            if ( queuedFact == fact ) {
                queueIterator.remove();
                // found it, don't need to keep going
                break;
            }
        }
        
        if ( fact.getComplexFactExpressions().isEmpty() ) {
            // can't affect any rules, so reset for the next iteration and don't add to the queue
            fact.reset();
        }
        else {
            // can affect rules, prioritize appropriately
            this.factsToEval.offer( fact );
        }
    }
    
    /**
     * Adds certain boot-strap objects (system settings primarily) to the context.
     */
    private void addUniversalContext() {
        // top level BRE will always need to add its own copy of system
        // settings, if one does not exist
    	this.incorporateNewContextObject(systemSettingsLogic.getCurrentSystemSettings());
    }
    
    /**
     * Finds all combinations of the provided domain object and objects in the context that match the types in the
     * provided array.
     */
    private List<List<PhoenixDomainObject>> calculateContextCombinations( final PhoenixDomainObject domainObject,
            final Set<Class<? extends PhoenixDomainObject>> constructorParamTypes ) {
        List<List<PhoenixDomainObject>> constructorParamCombos = new ArrayList<List<PhoenixDomainObject>>();
        
        // for each type needed by the constructor
        for ( final Class<? extends PhoenixDomainObject> paramType : constructorParamTypes ) {
            // create a list of context objects that matches the parameter type
            final ArrayList<PhoenixDomainObject> contextParamCombo =
                    this.matchProvidedOrContextObjectsToType( domainObject, paramType );
            
            if ( contextParamCombo.isEmpty() ) {
                // there are no matches for at least one parameter, so return an empty list.
                constructorParamCombos.clear();
                break;
            }
            else {
                constructorParamCombos = this.growOptionsMatrix( contextParamCombo, constructorParamCombos );
            }
        }
        return constructorParamCombos;
    }
    
    /**
     * Runs through the (prioritized) list of facts to evaluate ( {@link RulesEngine#evalFact}) and fires the rules that
     * are satisfied, in the order they are satisfied. Continues to loop through satisfied rules (and facts to evaluate)
     * until there are no further facts to be evaluated nor rules to fire.
     * 
     * @return <code>true</code> if any rules were executed; <code>false</code> otherwise.
     */
    private boolean evaluateFactsAndFireRules() {
        boolean ruleWasExecuted = false;
        
        Fact evalFact = this.nextFactToEval();
        while ( evalFact != null ) {
            RulesEngine.LOG.trace( "Evaluating fact: " + evalFact.getClass().getSimpleName() );
            final Set<ComplexFactExpression> expressions =
                    new HashSet<ComplexFactExpression>( evalFact.getComplexFactExpressions() );
            if ( ( expressions == null ) || expressions.isEmpty() ) {
                RulesEngine.LOG.trace( "  no expressions found. Discarding fact." );
                continue;
            }
            
            evalFact.isTrue();
            
            for ( final ComplexFactExpression complexExpression : expressions ) {
                if ( complexExpression.isDeterminedByEvaluatedFacts() ) {
                    if ( complexExpression.isTrue() ) {
                        // fire the satisfied rule
                        complexExpression.executeRule();
                        ruleWasExecuted = true;
                    }
                    this.removeExpressionFromEvalQueue( complexExpression );
                }
            }
            evalFact = this.nextFactToEval();
        }
        return ruleWasExecuted;
    }
    
    /**
     * Adds the provided list of options as another dimension to the provided matrix of option combinations. The result
     * will be a new matrix (list of lists) that has a size of n*c combinations where n is the number of elements in the
     * optionsList and c was the number of combinations in the previous matrix. For example:
     * 
     * <pre>
     *   previous matrix:  | A1 | B1 |
     *                     | A1 | B2 |
     *                     | A1 | B2 |
     *                     
     *   list to add:      [C1, C2, C3]
     *   
     *   new matrix:       | A1 | B1 | C1 |
     *                     | A1 | B1 | C2 |
     *                     | A1 | B1 | C3 |
     *                     | A1 | B2 | C1 |
     *                     | A1 | B2 | C2 |
     *                     | A1 | B2 | C3 |
     * </pre>
     */
    private List<List<PhoenixDomainObject>> growOptionsMatrix( final List<PhoenixDomainObject> optionList,
            final List<List<PhoenixDomainObject>> optionsMatrix ) {
        final List<List<PhoenixDomainObject>> largerMatrix = new ArrayList<List<PhoenixDomainObject>>();
        if ( optionsMatrix.isEmpty() ) {
            for ( final PhoenixDomainObject obj : optionList ) {
                final List<PhoenixDomainObject> newMatrixLine = new ArrayList<PhoenixDomainObject>();
                newMatrixLine.add( obj );
                largerMatrix.add( newMatrixLine );
            }
        }
        else {
            for ( final PhoenixDomainObject obj : optionList ) {
                for ( final List<PhoenixDomainObject> matrixLine : optionsMatrix ) {
                    final List<PhoenixDomainObject> newMatrixLine = new ArrayList<PhoenixDomainObject>( matrixLine );
                    newMatrixLine.add( obj );
                    largerMatrix.add( newMatrixLine );
                }
            }
        }
        return largerMatrix;
    }
    
    private void initialize() {
        if ( !this.initialized ) {
            this.rules = applicationContext.getBeansOfType( Rule.class, false, false ).values();
            
            if ( ( this.rules == null ) || this.rules.isEmpty() ) {
                RulesEngine.LOG.warn( "No Rules loaded from BeanFactory!" );
            }
            this.mapRules();
            this.initialized = true;
        }
    }
    
    /**
     * Causes the passed-in rule and its required facts to be added to the look-up maps.
     */
    private void mapRule( final Rule rule ) {
        final ComplexFactExpressionDefinition expression = rule.getExpressionDefinition();
        final Set<Class<? extends PhoenixDomainObject>> contextTypes = expression.getAllContextTypeNeeds();
        for ( final Class<? extends PhoenixDomainObject> contextType : contextTypes ) {
            this.expressionDefinitionsByContextType.put( contextType, expression );
        }
        final Set<Class<? extends Fact>> factNeeds = expression.getFactClasses();
        for ( final Class<? extends Fact> factNeed : factNeeds ) {
            this.rulesByFactType.put( factNeed, rule );
            this.addFactType( factNeed );
        }
    }
    
    /**
     * Causes all rules that have been loaded, along with the facts they require to be added to the look-up maps.
     */
    private void mapRules() {
        final Set<Class<? extends Rule>> ruleClasses = new HashSet<Class<? extends Rule>>();
        for ( final Rule rule : this.rules ) {
            final Class<? extends Rule> ruleClass = rule.getClass();
            if ( ruleClasses.contains( ruleClass ) ) {
                RulesEngine.LOG.error( "Duplicate rule loaded. Will ignore: " + ruleClass.getSimpleName() );
            }
            else {
                ruleClasses.add( ruleClass );
                this.mapRule( rule );
            }
        }
    }
    
    /**
     * Returns a list containing the single domain object provided if that object is of the specified type; otherwise
     * returns a list of all context objects that match that type.
     */
    private <T extends PhoenixDomainObject> ArrayList<PhoenixDomainObject> matchProvidedOrContextObjectsToType(
            final PhoenixDomainObject domainObject, final Class<T> type ) {
        final ArrayList<PhoenixDomainObject> contextParamCombo = new ArrayList<PhoenixDomainObject>();
        if ( domainObject.getClass() == type ) {
            contextParamCombo.add( domainObject );
        }
        else {
            final Collection<T> options = this.getContextObjectsOfType( type );
            if ( ( options != null ) && ( options.size() > 0 ) ) {
                contextParamCombo.addAll( options );
            }
        }
        return contextParamCombo;
    }
    
    /**
     * Determines the next fact to be evaluated from the list of facts that are pending evaluation.
     */
    private Fact nextFactToEval() {
        return this.factsToEval.poll();
    }
    
    /**
     * Removes the expression's facts from the eval queue if they are not used by other expressions remaining to be
     * evaluated.
     * 
     * @param expression
     *            The expression whose "orphaned" facts will be removed.
     */
    private void removeExpressionFromEvalQueue( final ComplexFactExpression expression ) {
        final Collection<Fact> expressionFacts = new ArrayList<Fact>( expression.getFacts() );
        expression.clearCacheAndRemoveFromFacts();
        for ( final Fact fact : expressionFacts ) {
            this.prioritizeFactInEvalQueue( fact );
        }
    }
    
    /**
     * Finds all mappings to this old version in the version map and updates them with the replacement.
     */
    private void replaceContextObjectVersionValue( final PhoenixDomainObject old, final PhoenixDomainObject replacement ) {
        final Set<Entry<PhoenixDomainObject, PhoenixDomainObject>> entries = this.contextObjectVersionMap.entrySet();
        for ( final Entry<PhoenixDomainObject, PhoenixDomainObject> entry : entries ) {
            if ( entry.getValue().equals( old ) ) {
                entry.setValue( replacement );
            }
        }
    }
    
    /**
     * Clear the facts to eval queue and reinitialize based on the expressions in memory. This allows
     */
    private void resetEvalQueue() {
        final Set<Fact> foundFacts = new HashSet<Fact>();
        this.factsToEval.clear();
        for ( final ComplexFactExpression expression : this.factExpressionByDefinition.valueSet() ) {
            foundFacts.addAll( expression.reAddFacts() );
            // final Collection<Fact> expressionFacts = expression.getFacts();
            // foundFacts.addAll(expressionFacts);
            // for (Fact fact : expressionFacts) {
            // fact.addComplexFactExpression(expression);
            // }
        }
        this.factsToEval.addAll( foundFacts );
    }
    
    /**
     * Called by {@link #replaceContext(PhoenixDomainObject, PhoenixDomainObject)} to maintain the fact/context mapping
     * with the most current context.
     */
    private void updateFactsByContext( final PhoenixDomainObject old, final PhoenixDomainObject replacement ) {
        final Collection<Fact> facts = this.factsByContext.get( old );
        this.factsByContext.removeKey( old );
        if ( facts != null ) {
            for ( final Fact fact : facts ) {
                this.factsByContext.put( replacement, fact );
            }
        }
    }

}
