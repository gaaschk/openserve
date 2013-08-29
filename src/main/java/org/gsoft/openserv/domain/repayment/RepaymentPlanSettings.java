package org.gsoft.openserv.domain.repayment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.domain.loan.LoanPhaseEvent;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;
import org.gsoft.openserv.util.time.FrequencyType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@RulesEngineEntity
public class RepaymentPlanSettings extends PersistentDomainObject{
	private static final long serialVersionUID = 5947871498220215709L;

	private Long repaymentPlanSettingsID;
	private String name;
	private String description;
	private LoanPhaseEvent planStartDate;
	private Boolean capInterestAtBegin = false;
	private FrequencyType capFrequency = FrequencyType.NONE;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getRepaymentPlanSettingsID() {
		return repaymentPlanSettingsID;
	}
	public void setRepaymentPlanSettingsID(Long repaymentPlanSettingsID) {
		this.repaymentPlanSettingsID = repaymentPlanSettingsID;
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
	@Type( type = "org.gsoft.openserv.util.jpa.GenericEnumUserType", parameters={
			@Parameter(name = "enumClass", value = "org.gsoft.openserv.domain.loan.LoanPhaseEvent")
	})
    @Column( name = "planStartDateID" )
	public LoanPhaseEvent getPlanStartDate() {
		return planStartDate;
	}
	public void setPlanStartDate(LoanPhaseEvent planStartDate) {
		this.planStartDate = planStartDate;
	}
	public Boolean getCapInterestAtBegin() {
		return capInterestAtBegin;
	}
	public void setCapInterestAtBegin(Boolean capInterestAtBegin) {
		this.capInterestAtBegin = capInterestAtBegin;
	}
	@Type( type = "org.gsoft.openserv.util.jpa.GenericEnumUserType", parameters={
			@Parameter(name = "enumClass", value = "org.gsoft.openserv.util.time.FrequencyType")
	})
    @Column( name = "capFrequencyID" )
	public FrequencyType getCapFrequency() {
		return capFrequency;
	}
	public void setCapFrequency(FrequencyType capFrequency) {
		this.capFrequency = capFrequency;
	}
	
	@Override
	@Transient
	public Long getID() {
		return this.getRepaymentPlanSettingsID();
	}
}
