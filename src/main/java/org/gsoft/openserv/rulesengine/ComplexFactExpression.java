package org.gsoft.openserv.rulesengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gsoft.openserv.domain.OpenServDomainObject;


public class ComplexFactExpression {
    private static final Logger LOG = LogManager.getLogger("ruleTrace");
    private ComplexFactExpressionDefinition definition;
    private List<FactExpression> simpleExpressions;
    private Boolean cachedTruth;
    /**
     * This is the simple expression that causes this expression to be true.
     * We use this to determine which expression to send to the rule when
     * when we execute it.  
     */
    private FactExpression firingExpression;

    public ComplexFactExpression(ComplexFactExpressionDefinition definition) {
        this.definition = definition;
        this.simpleExpressions = new ArrayList<FactExpression>();

        List<FactExpressionDefinition> simpleDefinitions = this.definition.getExpressionDefinitions();
        for (FactExpressionDefinition simpleDefinition : simpleDefinitions) {
            this.simpleExpressions.add(new FactExpression(this, simpleDefinition));
        }
    }

    public void addFact(Fact fact) {
        for (FactExpression expression : this.simpleExpressions) {
            expression.addFact(fact);
        }
        fact.addComplexFactExpression(this);
    }

    public boolean isTrue() {
        if (this.cachedTruth == null) {
            this.isDeterminedByEvaluatedFacts();
        }
        return this.cachedTruth == null ? false : this.cachedTruth;
    }
    
    public void executeRule() {
        if (this.firingExpression == null) {
            throw new RulesEngineException("Called executeRule without the expression evaluating to true.");
        }
        this.getRule().execute(this.firingExpression);
    }
    
    public Rule getRule() {
        return this.definition.getRule();
    }

    /**
     * Clears this expression for re-evaluation and detaches facts from it. Any cached values
     * are forgotten. The expression still uses the same definition and remembers its facts.
     */
    public void clearCacheAndRemoveFromFacts() {
        // BRE-TODO : Find a more efficient way to do this.
        for (Fact fact : this.getFacts()) {
            fact.removeComplexFactExpression(this);
        }
        for (FactExpression expression : this.simpleExpressions) {
            expression.clearCacheAndRemoveFromFacts();
        }
        this.cachedTruth = null;
        this.firingExpression = null;
    }
    
    public <T extends Fact> T getFactForClass(final Class<T> factClass) {
        T fact = null;
        for (FactExpression expression : this.simpleExpressions) {
            fact = expression.getFactForClass(factClass);
            if (fact != null) {
                break;
            }
        }
        return fact;
    }

    /**
     * Returns true if the facts that have been evaluated in this expression can determine the
     * truth or falseness of the expression. Note that this requires at least one underlying
     * expression to be determined "true" or for all expressions to be determined "false".
     */
    public boolean isDeterminedByEvaluatedFacts() {
        final Rule rule = this.definition.getRule();
        LOG.trace("Evaluating rule " + rule.getClass().getSimpleName() + " : " + rule);
        boolean allDeterminedFalse = true;
        for (FactExpression expression : this.simpleExpressions) {
            if (expression.isDeterminedByEvaluatedFacts()) {
                if (expression.isTrue()) {
                    LOG.trace("  Complex expression determined true as at least one expression is true.");
                    this.firingExpression = expression;
                    this.cachedTruth = true;
                    return true;
                }
            }
            else {
                // At least one is undetermined
                allDeterminedFalse = false;
            }
        }
        
        if (allDeterminedFalse) {
            LOG.trace("  Complex expression determined false as all expressions are false.");
            this.cachedTruth = false;
            return true;
        }
        else {
            LOG.trace("  Could not determine complex expression from available facts.");
            return false;
        }
    }

    public Collection<Fact> getFacts() {
        Set<Fact> facts = new HashSet<Fact>();
        for (FactExpression expression : this.simpleExpressions) {
            facts.addAll(expression.getFacts());
        }
        return facts;
    }

    public void gatherFacts(List<OpenServDomainObject> contextList, RulesEngine rulesEngine) {
        FactFinder finder = new FactFinder(contextList, this.definition.getFactClasses(), rulesEngine.getFactsByContext());
        
        Set<Fact> existingFacts = finder.getExistingNeededFacts();
        for (Fact fact : existingFacts) {
            this.addFact(fact);
            rulesEngine.prioritizeFactInEvalQueue(fact);
        }
        
        Set<Fact> newFacts = finder.createNonExistingNeededFacts();
        for (Fact fact : newFacts) {
            rulesEngine.indexNewFact(fact, fact.getContextObjects());
            fact.setEngine(rulesEngine);
            this.addFact(fact);
            rulesEngine.prioritizeFactInEvalQueue(fact);
        }
    }

    public Collection<? extends Fact> reAddFacts() {
        Collection<? extends Fact> facts = this.getFacts();
        for (Fact fact : facts) {
            this.addFact(fact);
        }
        return this.getFacts();
    }
}
