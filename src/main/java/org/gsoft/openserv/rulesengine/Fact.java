package org.gsoft.openserv.rulesengine;

import java.util.Set;

import org.gsoft.openserv.domain.PhoenixDomainObject;

public interface Fact {
    /**
     * Add the provided expression to the list of complex expressions that use this fact.
     * 
     * @param expression
     *            The new expression using this fact.
     */
    void addComplexFactExpression(ComplexFactExpression expression);

    /**
     * Add the provided expression to the list of expressions that use this fact.
     * 
     * @param expression
     *            The new expression using this fact.
     */
    void addFactExpression(FactExpression expression);
    
    /**
     * Return the set of complex expressions that use this fact.
     */
    Set<ComplexFactExpression> getComplexFactExpressions();

    /**
     * Return the list of context objects this fact uses in its evaluation.
     */
    public PhoenixDomainObject[] getContextObjects();
    
    /**
     * Return the set of expressions that use this fact.
     */
    Set<FactExpression> getFactExpressions();
    
    /**
     * Answer whether this fact has been evaluated or not. A fact will only be evaluated once.
     * 
     * @return <code>true</code> if this fact has already been evaluated; <code>false</code> otherwise.
     */
    boolean isEvaluated();
    
    /**
     * Answer whether this fact is "fast" or not. Fast facts are those which do not access external services, especially
     * the database.
     */
    boolean isFast();
    
    /**
     * Answer whether this fact is currently true.
     * 
     * @return <code>true</code> if this fact evaluates to true; <code>false</code> otherwise.
     */
    boolean isTrue();
    
    /**
     * Remove the provided complex expression from the list of those using this fact.
     * 
     * @param expression
     *            The expression to be removed.
     */
    void removeComplexFactExpression(ComplexFactExpression expression);

    /**
     * Remove the provided expression from the list of those using this fact.
     * 
     * @param expression
     *            The expression to be removed.
     */
    void removeFactExpression(FactExpression expression);
    
    /**
     * Tells this fact to clear any cached values (except context relationships) so that a subsequent call to
     * {@link #isEvaluated()} returns false and similarly a subsequent call to {@link #isTrue()} will evaluate the fact
     * again.
     */
    void reset();
    
    /**
     * provides access to the rules engine, which will be used to access and modify engine context when additional
     * (specific) context objects need to be retrieved (for example, by ID)
     * 
     * @param engineContext
     */
    void setEngine(RulesEngine rulesEngine);
}
