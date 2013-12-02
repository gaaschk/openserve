package org.gsoft.openserv.domain.loan;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.domain.repayment.RepaymentPlan;
import org.gsoft.openserv.util.time.FrequencyType;
import org.gsoft.openserv.web.support.formatter.currency.CurrencyInPenniesFormat;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class DefaultLoanProgramSettings extends PersistentDomainObject{
	private static final long serialVersionUID = 6245557328161056820L;
	private Long defaultLoanProgramSettingsID;
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date effectiveDate;
	@NumberFormat
	private Integer maximumLoanTerm;
	@NumberFormat
	private Integer graceMonths;
	@NumberFormat
	private Integer minDaysToFirstDue;
	@NumberFormat
	private Integer prepaymentDays;
	@NumberFormat
	private Integer daysBeforeDueToBill;
	@NumberFormat
	private Integer daysLateForFee;
	private Integer lateFeeAmount;
	private Boolean isVariableRate;
	//Enumerations
	private LoanProgram loanProgram;
	private RepaymentStartType repaymentStartType;
	private FrequencyType baseRateUpdateFrequency;
	//Relationships
	private Rate baseRate;
	private List<RepaymentPlan> repaymentPlanList;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getDefaultLoanProgramSettingsID() {
		return defaultLoanProgramSettingsID;
	}
	public void setDefaultLoanProgramSettingsID(Long defaultLoanProgramSettingsID) {
		this.defaultLoanProgramSettingsID = defaultLoanProgramSettingsID;
	}
	@ManyToOne
	@JoinColumn( name = "LoanProgramID" )
	public LoanProgram getLoanProgram() {
		return loanProgram;
	}
	public void setLoanProgram(LoanProgram loanProgram) {
		this.loanProgram = loanProgram;
	}
	public Date getEffectiveDate() {
		return (effectiveDate==null)?null:(Date)effectiveDate.clone();
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = (effectiveDate==null)?null:(Date)effectiveDate.clone();
	}
	public Integer getMaximumLoanTerm() {
		return maximumLoanTerm;
	}
	public void setMaximumLoanTerm(Integer maximumLoanTerm) {
		this.maximumLoanTerm = maximumLoanTerm;
	}
	public Integer getGraceMonths() {
		return graceMonths;
	}
	public void setGraceMonths(Integer graceMonths) {
		this.graceMonths = graceMonths;
	}
	public Integer getMinDaysToFirstDue() {
		return minDaysToFirstDue;
	}
	public void setMinDaysToFirstDue(Integer minDaysToFirstDue) {
		this.minDaysToFirstDue = minDaysToFirstDue;
	}
	public Integer getPrepaymentDays() {
		return prepaymentDays;
	}
	public void setPrepaymentDays(Integer prepaymentDays) {
		this.prepaymentDays = prepaymentDays;
	}
	public Integer getDaysBeforeDueToBill() {
		return daysBeforeDueToBill;
	}
	public void setDaysBeforeDueToBill(Integer daysBeforeDueToBill) {
		this.daysBeforeDueToBill = daysBeforeDueToBill;
	}
	public Integer getDaysLateForFee(){
		return this.daysLateForFee;
	}
	public void setDaysLateForFee(Integer daysLateForFee){
		this.daysLateForFee = daysLateForFee;
	}
	@CurrencyInPenniesFormat
	public Integer getLateFeeAmount(){
		return this.lateFeeAmount;
	}
	public void setLateFeeAmount(Integer lateFeeAmount){
		this.lateFeeAmount = lateFeeAmount;
	} 

	@Column(columnDefinition = "SMALLINT")
	public Boolean isVariableRate() {
		return isVariableRate;
	}
	public void setVariableRate(Boolean isVariableRate) {
		this.isVariableRate = isVariableRate;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getDefaultLoanProgramSettingsID();
	}
	@Type( type = "org.gsoft.openserv.util.jpa.GenericEnumUserType", parameters={
			@Parameter(name = "enumClass", value = "org.gsoft.openserv.domain.loan.RepaymentStartType")
	})
    @Column( name = "RepaymentStartTypeID" )
	public RepaymentStartType getRepaymentStartType() {
		return repaymentStartType;
	}
	public void setRepaymentStartType(RepaymentStartType repaymentStartType) {
		this.repaymentStartType = repaymentStartType;
	}
	@Type( type = "org.gsoft.openserv.util.jpa.GenericEnumUserType", parameters={
			@Parameter(name = "enumClass", value = "org.gsoft.openserv.util.time.FrequencyType")
	})
    @Column( name = "BaseRateUpdateFrequencyID" )
	public FrequencyType getBaseRateUpdateFrequency() {
		return baseRateUpdateFrequency;
	}
	public void setBaseRateUpdateFrequency(FrequencyType baseRateUpdateFrequency) {
		this.baseRateUpdateFrequency = baseRateUpdateFrequency;
	}
	@ManyToOne
	@JoinColumn(name="BaseRateID")
	public Rate getBaseRate() {
		return baseRate;
	}
	public void setBaseRate(Rate baseRate) {
		this.baseRate = baseRate;
	}
	@OneToMany(mappedBy="defaultLoanProgramSettings")
	public List<RepaymentPlan> getRepaymentPlanList() {
		return repaymentPlanList;
	}
	public void setRepaymentPlanList(List<RepaymentPlan> repaymentPlanList) {
		this.repaymentPlanList = repaymentPlanList;
	}
}
