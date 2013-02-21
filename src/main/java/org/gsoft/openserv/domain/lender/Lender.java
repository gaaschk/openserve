package org.gsoft.openserv.domain.lender;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;


@Entity
@RulesEngineEntity
public class Lender extends PersistentDomainObject{
	private static final long serialVersionUID = 216492318421412949L;
	private Long lenderID;
	private String name;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getLenderID() {
		return lenderID;
	}
	public void setLenderID(Long lenderID) {
		this.lenderID = lenderID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Transient
	@Override
	public Long getID(){
		return this.getLenderID();
	}
}
