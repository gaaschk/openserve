package org.gsoft.openserv.domain;

import java.io.Serializable;

public abstract class PersistentDomainObject implements Serializable {
	private static final long serialVersionUID = -3050035343078844357L;

	@Override
    public int hashCode() {
        final int prime = 31;
        return prime + ( ( this.getID() == null ) ? 0 : this.getID().hashCode() );
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
            final PersistentDomainObject other = (PersistentDomainObject)obj;
            if ( this.equalsByID( other )) {
                return true;
            }
        }
        return false;
    }

    protected boolean equalsByID( final PersistentDomainObject other ) {
        if ( this.getID() == null ) {
            return other.getID() == null;
        }
        else {
            return this.getID().equals( other.getID() );
        }
    }

    public abstract Long getID();
}
