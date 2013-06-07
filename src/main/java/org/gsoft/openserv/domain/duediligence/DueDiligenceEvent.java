package org.gsoft.openserv.domain.duediligence;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class DueDiligenceEvent extends PersistentDomainObject {
	private static final long serialVersionUID = 7006486810668602993L;

	private Long dueDiligenceEventID;
	private DueDiligenceEventType dueDiligenceEventType;
	private DueDiligenceSchedule dueDiligenceSchedule;
	private Integer minDelqDays;
	private Integer maxDelqDays;
	private Integer defaultDelqDays;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getDueDiligenceEventID() {
		return dueDiligenceEventID;
	}

	public void setDueDiligenceEventID(Long dueDiligenceEventID) {
		this.dueDiligenceEventID = dueDiligenceEventID;
	}

	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn( name = "DueDiligenceEventTypeID" )
	public DueDiligenceEventType getDueDiligenceEventType() {
		return dueDiligenceEventType;
	}

	public void setDueDiligenceEventType(DueDiligenceEventType dueDiligenceEventType) {
		this.dueDiligenceEventType = dueDiligenceEventType;
	}

	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn( name = "DueDiligenceScheduleID" )
	public DueDiligenceSchedule getDueDiligenceSchedule() {
		return dueDiligenceSchedule;
	}

	public void setDueDiligenceSchedule(DueDiligenceSchedule dueDiligenceSchedule) {
		this.dueDiligenceSchedule = dueDiligenceSchedule;
	}

	public Integer getMinDelqDays() {
		return minDelqDays;
	}

	public void setMinDelqDays(Integer minDelqDays) {
		this.minDelqDays = minDelqDays;
	}

	public Integer getMaxDelqDays() {
		return maxDelqDays;
	}

	public void setMaxDelqDays(Integer maxDelqDays) {
		this.maxDelqDays = maxDelqDays;
	}

	public Integer getDefaultDelqDays() {
		return defaultDelqDays;
	}

	public void setDefaultDelqDays(Integer defaultDelqDays) {
		this.defaultDelqDays = defaultDelqDays;
	}
	
	@Override
	@Transient
	public Long getID() {
		return this.getDueDiligenceEventID();
	}
}
