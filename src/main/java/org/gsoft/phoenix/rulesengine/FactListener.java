package org.gsoft.phoenix.rulesengine;

/**
 * Implementors of this interface are interested in knowing when aspects of facts change.
 * Specifically, listeners will be notified when a fact has been evaluated.
 */
public interface FactListener {
    /**
     * Called by the fact when it has been evaluated.
     * 
     * @param fact The fact that has been evaluated.
     */
    public void factEvaluated(Fact fact);
}
