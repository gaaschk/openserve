package org.gsoft.openserv.rulesengine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.Predicate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gsoft.openserv.domain.OpenServDomainObject;

public abstract class AbstractFact implements Fact {
    protected static final Logger LOG = LogManager.getLogger( "ruleTrace" );
    
    // fact truth values are intentionally nullable to indicate unevaluated facts
    Boolean isTrue = null;
    private final Set<ComplexFactExpression> complexExpressions;
    private final OpenServDomainObject[] contextObjects;
    private final Set<FactExpression> factExpressions;
    private RulesEngine rulesEngine;
    
    protected AbstractFact( final OpenServDomainObject... contextObjects ) {
        this.complexExpressions = new HashSet<ComplexFactExpression>();
        this.factExpressions = new HashSet<FactExpression>();
        this.contextObjects = contextObjects;
    }
    
    public void addComplexFactExpression( final ComplexFactExpression expression ) {
        this.complexExpressions.add( expression );
    }
    
    public void addFactExpression( final FactExpression expression ) {
        this.factExpressions.add( expression );
    }
    
    public <T extends OpenServDomainObject> T findInContext( final T potentialMatch ) {
        return this.rulesEngine.findInContext( potentialMatch );
    }
    
    public Set<ComplexFactExpression> getComplexFactExpressions() {
        return this.complexExpressions;
    }
    
    public OpenServDomainObject[] getContextObjects() {
        return this.contextObjects;
    }
    
    public Set<FactExpression> getFactExpressions() {
        return Collections.unmodifiableSet( this.factExpressions );
    }
    
    public boolean isEvaluated() {
        return this.isTrue != null;
    }
    
    public boolean isTrue() {
        if ( !this.isEvaluated() ) {
        	this.refreshFromContext();
            this.isTrue = this.evaluate();
        }
        
        return this.isTrue;
    }
    
    public void removeComplexFactExpression( final ComplexFactExpression expression ) {
        this.complexExpressions.remove( expression );
    }
    
    public void removeFactExpression( final FactExpression expression ) {
        this.factExpressions.remove( expression );
    }
    
    public void reset() {
        this.isTrue = null;
    }
    
    public void setEngine( final RulesEngine rulesEngine ) {
        this.rulesEngine = rulesEngine;
        
    }
    
    protected void addToContext( final OpenServDomainObject... openServDomainObjects ) {
        this.rulesEngine.addContext( openServDomainObjects );
    }
    
    protected abstract boolean evaluate();
    
    protected abstract void refreshFromContext();
    
    protected <T extends OpenServDomainObject> T findInContext( final Class<T> contextClass, final Predicate predicate ) {
        return this.rulesEngine.findInContext( contextClass, predicate );
    }
    
    protected void trace( final String message ) {
        // this.LOG.trace(message);
    }
}
