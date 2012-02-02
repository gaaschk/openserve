package org.gsoft.openserv.rulesengine;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.gsoft.openserv.domain.OpenServDomainObject;

/**
 * Instances of this class are lists of Fact classes paired with expected truth values. An expression definition,
 * therefore, does not explicitly evaluate to "true" or "false". However, given a set of Fact instances (facts) an
 * expression could be evaluated against them. Note that such an expression only evaluates to "true" if all of the facts
 * evaluate to boolean values that match their expected values in the expression definition. In other words, the
 * fact/value list performs a logical "and".
 */
public class FactExpressionDefinition {
    private final Map<Class<? extends Fact>, Boolean> expressionStore;
    private final Set<Class<? extends OpenServDomainObject>> contextTypeNeeds;
    private final Rule rule;
    
    public FactExpressionDefinition(Rule rule) {
        this.rule = rule;
        this.expressionStore = new HashMap<Class<? extends Fact>, Boolean>();
        this.contextTypeNeeds = new HashSet<Class<? extends OpenServDomainObject>>();
    }
    
    public Rule getRule() {
        return this.rule;
    }
    
    /**
     * Add a new expectation to this expression definition. Note that an expression can hold only one expectation for a
     * given type of fact. That is, if the same factClass is added more than once, only the last addition will matter.
     * 
     * @param factClass
     *            The type of fact to add.
     * @param expectedValue
     *            Whether the fact is expected to be true or false.
     */
    public void addFactExpectation(final Class<? extends Fact> factClass, final boolean expectedValue) {
        this.expressionStore.put(factClass, expectedValue);
        this.addContextTypeNeeds(FactUtil.determineConstructorTypesForFactType(factClass));
    }
    
    /**
     * Returns an unmodifiable set containing all of the domain object classes for which facts in this expression
     * definition have a need.
     */
    public Set<Class<? extends OpenServDomainObject>> getAllContextTypeNeeds() {
        return Collections.unmodifiableSet(this.contextTypeNeeds);
    }
    
    /**
     * Returns an unmodifiable set containing all of the fact classes for which this expression definition has an
     * expectation.
     */
    public Set<Class<? extends Fact>> getFactClasses() {
        return Collections.unmodifiableSet(this.expressionStore.keySet());
    }
    
    /**
     * Returns the true or false expectation for the provided fact class or null if this expression definition is not
     * interested in (has no expectation for) the provided class.
     * 
     * @param factClass
     *            The class whose associated expectation is to be returned.
     * @return The expectation (true/false) or null if there is no expectation.
     */
    public boolean getFactExpectation(final Class<? extends Fact> factClass) {
        return this.expressionStore.get(factClass);
    }
    
    /**
     * Returns the true or false expectation for the class of the provided fact or null if this expression definition is
     * not interested in (has no expectation for) the class. Equivalent to getFactExpectation(fact.getClass()).
     * 
     * @param fact
     *            The fact to whose associated expectation is to be returned.
     * @return The expectation (true/false) or null if there is no expectation.
     */
    public boolean getFactExpectation(final Fact fact) {
        return this.getFactExpectation(fact.getClass());
    }
    
    /**
     * Returns true if this expression definition has an expectation associated with the provided Fact subclass.
     * 
     * @param factClass
     *            The class for which the presence of an expectation will be checked.
     * @return true if this expression definition has an expectation for the provided type.
     */
    public boolean hasExpectation(final Class<? extends Fact> factClass) {
        return this.expressionStore.containsKey(factClass);
    }
    
    /**
     * Returns true if this expression definition has an expectation associated with the provided Fact's class.
     * Equivalent to calling hasExpectation(fact.getClass())
     * 
     * @param fact
     *            The Fact for which the presence of an expectation will be checked.
     * @return true if this expression definition has an expectation for the provided Fact's type.
     */
    public boolean hasExpectation(final Fact fact) {
        return this.hasExpectation(fact.getClass());
    }
    
    /**
     * Adds each domain type to the set of context types this expression definition's facts need.
     * If there was already a need for that type, this method effectively does nothing.
     */
    private void addContextTypeNeeds(final Class<? extends OpenServDomainObject>[] domainTypes) {
        for (final Class<? extends OpenServDomainObject> domainType : domainTypes) {
            this.contextTypeNeeds.add(domainType);
        }
    }
}
