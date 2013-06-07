package org.gsoft.openserv.domain.duediligence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class DueDiligenceEventType extends PersistentDomainObject{
	private static final long serialVersionUID = -7855594877683831575L;

	private Long dueDiligenceEventTypeID;
	private String name;
	private String description;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getDueDiligenceEventTypeID() {
		return dueDiligenceEventTypeID;
	}
	public void setDueDiligenceEventTypeID(Long dueDiligenceEventTypeID) {
		this.dueDiligenceEventTypeID = dueDiligenceEventTypeID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Transient
	@Override
	public Long getID() {
		return this.getDueDiligenceEventTypeID();
	}
}
