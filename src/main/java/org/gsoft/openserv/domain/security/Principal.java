package org.gsoft.openserv.domain.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;

@Entity
@Table(name="SecPrincipal")
@Inheritance(strategy=InheritanceType.JOINED)
public class Principal extends PersistentDomainObject{
	private static final long serialVersionUID = -4382980089519074792L;
	private Long principalID;
	private String name;
	private Boolean isActive = true;
	
	@Id
    @GeneratedValue( strategy=GenerationType.AUTO )
	public Long getPrincipalID() {
		return principalID;
	}
	public void setPrincipalID(Long principalID) {
		this.principalID = principalID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean isActive() {
		return isActive;
	}
	public void setActive(Boolean active) {
		this.isActive = active;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getPrincipalID();
	}
}
