package org.gsoft.openserv.rulesengine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public abstract class AbstractRule implements Rule {
    protected static final Logger LOG = LogManager.getLogger( "ruleTrace" );
    
    protected final ComplexFactExpressionDefinition expression;
    
    public AbstractRule() {
        this.expression = new ComplexFactExpressionDefinition( this );
    }
    
    public abstract void execute( final FactExpression factExpression );
    
    public ComplexFactExpressionDefinition getExpressionDefinition() {
        return this.expression;
    }
    
}
