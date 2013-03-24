package org.gsoft.openserv.domain.loan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;
import org.gsoft.openserv.util.time.FrequencyType;
import org.gsoft.openserv.web.support.formatter.currency.CurrencyInPenniesFormat;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
@RulesEngineEntity
public class LenderLoanProgramSettings extends PersistentDomainObject {
	private static final long serialVersionUID = 2305820144103922657L;
	private Long loanProgramID;
	private Long lenderID;
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date programBeginDate;
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date programEndDate;
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
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getLoanProgramID() {
		return loanProgramID;
	}
	public void setLoanProgramID(Long loanProgramID) {
		this.loanProgramID = loanProgramID;
	}
	public Long getLenderID() {
		return lenderID;
	}
	public void setLenderID(Long lenderID) {
		this.lenderID = lenderID;
	}
	@ManyToOne
	@JoinColumn( name = "LoanProgramID" )
	public LoanProgram getLoanProgram() {
		return loanProgram;
	}
	public void setLoanProgram(LoanProgram loanProgram) {
		this.loanProgram = loanProgram;
	}
	public Date getProgramBeginDate() {
		return (programBeginDate==null)?null:(Date)programBeginDate.clone();
	}
	public void setProgramBeginDate(Date effectiveDate) {
		this.programBeginDate = (effectiveDate==null)?null:(Date)effectiveDate.clone();
	}
	public Date getProgramEndDate() {
		return (programEndDate==null)?null:(Date)programEndDate.clone();
	}
	public void setProgramEndDate(Date endDate) {
		this.programEndDate = (endDate==null)?null:(Date)endDate.clone();
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
	@Override
	@Transient
	public Long getID() {
		return this.getLoanProgramID();
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
	public Boolean isVariableRate() {
		return isVariableRate;
	}
	public void setVariableRate(Boolean variableRate) {
		this.isVariableRate = variableRate;
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
}
