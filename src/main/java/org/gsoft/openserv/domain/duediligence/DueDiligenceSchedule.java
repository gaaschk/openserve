package org.gsoft.openserv.domain.duediligence;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class DueDiligenceSchedule extends PersistentDomainObject {
	private static final long serialVersionUID = 1626058932364063709L;
	private Long dueDiligenceScheduleID;
	private LoanProgram loanProgram;
	private Date effectiveDate;
	//Relationships
	private List<DueDiligenceEvent> events;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getDueDiligenceScheduleID() {
		return dueDiligenceScheduleID;
	}

	public void setDueDiligenceScheduleID(Long dueDiligenceScheduleID) {
		this.dueDiligenceScheduleID = dueDiligenceScheduleID;
	}

	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn( name = "LoanProgramID" )
	public LoanProgram getLoanProgram() {
		return loanProgram;
	}

	public void setLoanProgram(LoanProgram loanProgram) {
		this.loanProgram = loanProgram;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@OneToMany(mappedBy="dueDiligenceSchedule")
	public List<DueDiligenceEvent> getEvents() {
		return events;
	}

	public void setEvents(List<DueDiligenceEvent> events) {
		this.events = events;
	}

	@Override
	@Transient
	public Long getID() {
		return this.getDueDiligenceScheduleID();
	}
}
