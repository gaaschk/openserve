package org.gsoft.phoenix.rulesengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

/**
 * Instances of this class are lists of "simple" FactExpressionDefinitions. As this is a definition,
 * it does not explicitly evaluate to "true" or "false". However, given a set of Fact instances (facts) it
 * could be evaluated against them. Such an expression evaluates to "true" if any of the fact expressions
 * in its list evaluates to "true". In other words, the complex expression is a logical "or" of its list of
 * expressions.
 * 
 * This class is designed for use in a "fluent" manner. That is, the "and" and "or" methods return the
 * definition's instance. An example usage might be:
 * 
 * <pre>
 *     definition.expect(fact1, true)
 *             .and(fact2, true)
 *             .or(fact2, false)
 *             .and(fact3, true);
 * </pre>
 * 
 * That usage results in a definition logically equivalent to the following:
 * 
 * <pre>
 *     (fact1.isTrue() && fact2.isTrue()) || (!fact2.isTrue() && fact3.isTrue()) 
 * </pre>
 * 
 * Note the parentheses around the "&&" ("AND") expressions. Precedence is such that "and" will always
 * take precedence over "or". This is a consequence of the underlying implementation, which is as a list
 * of "simple" expression definitions, which can only perform logical "and".
 */
public class ComplexFactExpressionDefinition {

    private List<FactExpressionDefinition> definitions;
    private Rule rule;
    
    /**
     * Creates a new definition for the specified rule instance.
     */
    public ComplexFactExpressionDefinition(Rule rule) {
        this.rule = rule;
        this.definitions = new ArrayList<FactExpressionDefinition>();
    }

    /**
     * Returns the rule which "owns" this definition.
     */
    public Rule getRule() {
        return this.rule;
    }

    /**
     * Adds a new expectation to this definitions' current "and" phrase. Note that such a phrase can hold only one
     * meaningful expectation for a given type of fact. That is, if the same factClass is added more than once to the
     * same "and" phrase, only the last addition will matter. Earlier ones will be dropped.
     * 
     * This version of the mehod was created to allow expressions to "read" better:
     * <pre>    expression.expect(aFact, true).and(anotherFact, false);
     * </pre>
     * 
     * @param factClass
     *            The type of fact to add.
     * @param expectedValue
     *            Whether the fact is expected to be true or false.
     * @return This definition.
     */
    public ComplexFactExpressionDefinition expect(Class<? extends Fact> factClass, boolean expectedValue) {
        return this.and(factClass, expectedValue);
    }

    /**
     * Adds a new expectation to this definitions' current "and" phrase. Note that such a phrase can hold only one
     * meaningful expectation for a given type of fact. That is, if the same factClass is added more than once to the
     * same "and" phrase, only the last addition will matter. Earlier ones will be dropped.
     * 
     * @param factClass
     *            The type of fact to add.
     * @param expectedValue
     *            Whether the fact is expected to be true or false.
     * @return This definition.
     */
    public ComplexFactExpressionDefinition and(Class<? extends Fact> factClass, boolean expectedValue) {
        FactExpressionDefinition definition;
        
        if (this.definitions.isEmpty()) {
            definition = this.createFactExpressionDefinition();
        }
        else {
            definition = this.definitions.get(this.definitions.size() - 1);
        }
        
        definition.addFactExpectation(factClass, expectedValue);
        
        return this;
    }

    /**
     * Creates a new "and" phrase and adds the specified expectation to it. The new phrase will be logically "or'd"
     * to any earlier "and" phrases. See the class-level documentation for more on how definitions are built and 
     * the precedence of "and" and "or".
     *  
     * @param factClass
     *            The type of fact to add.
     * @param expectedValue
     *            Whether the fact is expected to be true or false.
     * @return This definition.
     */
    public ComplexFactExpressionDefinition or(Class<? extends Fact> factClass, boolean expectedValue) {
        FactExpressionDefinition definition = this.createFactExpressionDefinition();
        definition.addFactExpectation(factClass, expectedValue);
        
        return this;
    }

    /**
     * Returns true if this definition has an expectation associated with the provided Fact subclass.
     * 
     * @param factClass
     *            The class for which the presence of an expectation will be checked.
     * @return true if this definition has an expectation for the provided type.
     */
    public boolean hasExpectation(Class<? extends Fact> factClass) {
        for (FactExpressionDefinition definition : this.definitions) {
            if (definition.hasExpectation(factClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if this expression definition has an expectation associated with the provided Fact's class.
     * Equivalent to calling hasExpectation(fact.getClass())
     * 
     * @param fact
     *            The Fact for which the presence of an expectation will be checked.
     * @return true if this expression definition has an expectation for the provided Fact's type.
     */
    public boolean hasExpectation(Fact fact) {
        return this.hasExpectation(fact.getClass());
    }

    /**
     * Returns a set containing all of the fact classes for which this expression definition has an
     * expectation.
     */
    public Set<Class<? extends Fact>> getFactClasses() {
        Set<Class<? extends Fact>> factClasses = new HashSet<Class<? extends Fact>>();
        
        for (FactExpressionDefinition definition : this.definitions) {
            factClasses.addAll(definition.getFactClasses());
        }
        
        return factClasses;
    }

    /**
     * Returns a set containing all of the domain object classes for which facts in this expression
     * definition have a need.
     */
    public Set<Class<? extends PhoenixDomainObject>> getAllContextTypeNeeds() {
        Set<Class<? extends PhoenixDomainObject>> contextTypes = new HashSet<Class<? extends PhoenixDomainObject>>();
        
        for (FactExpressionDefinition definition : this.definitions) {
            contextTypes.addAll(definition.getAllContextTypeNeeds());
        }
        
        return contextTypes;
    }

    private FactExpressionDefinition createFactExpressionDefinition() {
        FactExpressionDefinition definition;
        definition = new FactExpressionDefinition(this.getRule());
        this.definitions.add(definition);
        return definition;
    }

    /**
     * Returns this definition's list of simple definitions as an unmodifiable List.
     */
    public List<FactExpressionDefinition> getExpressionDefinitions() {
        return Collections.unmodifiableList(this.definitions);
    }
}
