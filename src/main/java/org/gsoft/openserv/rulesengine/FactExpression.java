package org.gsoft.openserv.rulesengine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FactExpression {
    private static final Logger LOG = LogManager.getLogger("ruleTrace");

    private final ComplexFactExpression parent;

    private final FactExpressionDefinition definition;
    
    private final Map<Class<? extends Fact>, Fact> facts;

    private Boolean cachedTruth;


    public FactExpression(final ComplexFactExpression parent, final FactExpressionDefinition definition) {
        this.parent = parent;
        this.definition = definition;
        this.facts = new HashMap<Class<? extends Fact>, Fact>();
    }
    
    public void executeRule() {
        this.getRule().execute(this);
    }
    
    public Rule getRule() {
        return this.definition.getRule();
    }

    /**
     * Adds the specified fact to the list of concrete facts this expression can use in its
     * evaluation of its truth or falseness. If this expression has no need for the fact,
     * it is ignored.
     */
    public void addFact(final Fact fact) {
        if (this.definition.hasExpectation(fact)) {
            this.facts.put(fact.getClass(), fact);
            fact.addFactExpression(this);
        }
    }
    
    /**
     * Clears this expression for re-evaluation and detaches facts from it. Any cached values
     * are forgotten. The expression still uses the same definition and remembers its facts.
     */
    public void clearCacheAndRemoveFromFacts() {
        for (Fact fact : this.facts.values()) {
            fact.removeFactExpression(this);
        }
        this.cachedTruth = null;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Fact> T getFactForClass(final Class<T> factClass) {
        return (T) this.facts.get(factClass);
    }
    
    public boolean isTrue() {
        if (this.cachedTruth == null) {
            this.isDeterminedByEvaluatedFacts();
        }
        return this.cachedTruth == null ? false : this.cachedTruth;
    }
    
    /**
     * Returns true if the facts that have been evaluated in this expression can determine the
     * truth or falseness of the expression. Note that any evaluated fact that is contrary to
     * this expression's expectations will mean the expression will be false regardless of
     * the other facts. Of course, if all facts have been evaluated, then this expression can
     * also be determined.
     */
    public boolean isDeterminedByEvaluatedFacts() {
        if (this.facts.isEmpty()) {
            LOG.trace("    could not determine expression as there are no evaluated facts.");
            return false;
        }
        
        boolean unevaluatedFacts = false;
        for (Class<? extends Fact> factClass : this.definition.getFactClasses()) {
            Fact fact = this.facts.get(factClass);
            if (fact == null || fact.isEvaluated() == false) {
                unevaluatedFacts = true;
            }
            else {
                boolean expectation = this.definition.getFactExpectation(factClass);
                if (fact.isTrue() != expectation) {
                    this.cachedTruth = false;
                    LOG.trace("    expression determined false by " + fact.getClass().getSimpleName());
                    return true;
                }
            }
        }
        
        if (unevaluatedFacts) {
            LOG.trace("    could not determine from evaluated facts. Evaluated: " + this.listEvaluatedFacts());
            return false;
        }
        else {
            this.cachedTruth = true;
            LOG.trace("    expression determined true. Evaluated: " + this.listEvaluatedFacts());
            return true;
        }
    }
    
    private String listEvaluatedFacts() {
        StringBuilder list = new StringBuilder();
        for (Fact fact : this.facts.values()) {
            if (fact.isEvaluated()) {
                list.append(fact.getClass().getSimpleName()).append(" ");
            }
        }
        return list.length() == 0 ? "none" : list.toString();
    }

    public Collection<Fact> getFacts() {
        return this.facts.values();
    }

    public ComplexFactExpression getParentExpression() {
        return this.parent;
    }
}
