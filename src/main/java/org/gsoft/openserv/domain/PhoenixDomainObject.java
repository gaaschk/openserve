package org.gsoft.openserv.domain;

import java.io.Serializable;

public abstract class PhoenixDomainObject implements Serializable {
	private static final long serialVersionUID = -3050035343078844357L;

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = prime + ( ( this.getID() == null ) ? 0 : this.getID().hashCode() );
        return result;
    }

	@Override
    public boolean equals( final Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        
        if ( this.getClass().equals( obj.getClass() ) ) {
            final PhoenixDomainObject other = (PhoenixDomainObject)obj;
            if ( this.equalsByID( other )) {
                return true;
            }
        }
        return false;
    }

    protected boolean equalsByID( final PhoenixDomainObject other ) {
        if ( this.getID() == null ) {
            return other.getID() == null;
        }
        else {
            return this.getID().equals( other.getID() );
        }
    }

    public abstract Long getID();
}
