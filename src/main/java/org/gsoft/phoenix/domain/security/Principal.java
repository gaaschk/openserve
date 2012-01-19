package org.gsoft.phoenix.domain.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

@Entity
@Table(name="SecPrincipal")
@Inheritance(strategy=InheritanceType.JOINED)
public class Principal implements PhoenixDomainObject{
	private static final long serialVersionUID = -4382980089519074792L;
	private Long principalID;
	private String name;
	private Boolean active = true;
	
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
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
}
