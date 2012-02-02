package org.gsoft.openserv.rulesengine;

/**
 * Implementations of this interface represent rules in the business rules engine.
 * For the purposes of the BRE, rules are things that should fire (execute) when
 * the facts in the BRE context can create a fact expression to satisfy the rules'
 * fact expression definitions. 
 */
public interface Rule {
    
    /**
     * Perform this rule's action.
     * 
     * @param factExpression The factExpression that led to this rule being executed.
     */
    void execute(final FactExpression factExpression);
    
    /**
     * Return the FactExpressionDefinition for this rule.
     * When a FactExpression can be built from this definition and that expression
     * evaluate to "true", this rule should be executed. 
     */
//    FactExpressionDefinition getFactExpressionDefinition();

    ComplexFactExpressionDefinition getExpressionDefinition();    
}
