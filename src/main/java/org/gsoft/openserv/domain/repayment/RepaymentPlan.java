package org.gsoft.openserv.domain.repayment;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.domain.loan.LoanPhaseEvent;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@RulesEngineEntity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class RepaymentPlan extends PersistentDomainObject{
	private static final long serialVersionUID = 5947871498220215709L;

	private Long repaymentPlanID;
	private LoanPhaseEvent planStartDate;
	private Integer graceMonths;
	
	private DefaultLoanProgramSettings defaultLoanProgramSettings;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getRepaymentPlanID() {
		return repaymentPlanID;
	}
	public void setRepaymentPlanID(Long repaymentPlanID) {
		this.repaymentPlanID = repaymentPlanID;
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
	public Integer getGraceMonths() {
		return graceMonths;
	}
	public void setGraceMonths(Integer graceMonths) {
		this.graceMonths = graceMonths;
	}
	@ManyToOne
	@JoinColumn(name="defaultLoanProgramSettingsID")
	public DefaultLoanProgramSettings getDefaultLoanProgramSettings() {
		return defaultLoanProgramSettings;
	}
	public void setDefaultLoanProgramSettings(DefaultLoanProgramSettings defaultLoanProgramSettings) {
		this.defaultLoanProgramSettings = defaultLoanProgramSettings;
		if(defaultLoanProgramSettings != null){
			List<RepaymentPlan> repaymentPlanList = defaultLoanProgramSettings.getRepaymentPlanList();
			if(repaymentPlanList == null){
				defaultLoanProgramSettings.setRepaymentPlanList(new ArrayList<RepaymentPlan>());
			}
			defaultLoanProgramSettings.getRepaymentPlanList().add(this);
		}
	}
	@Override
	@Transient
	public Long getID() {
		return this.getRepaymentPlanID();
	}
}
