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
import org.gsoft.openserv.util.time.FrequencyType;
import org.gsoft.openserv.web.formatter.currency.CurrencyInPenniesFormat;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class LoanTypeProfile extends PersistentDomainObject{
	private static final long serialVersionUID = 6245557328161056820L;
	private Long loanTypeProfileID;
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date effectiveDate;
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date endDate;
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
	private LoanType loanType;
	private RepaymentStartType repaymentStartType;
	private FrequencyType baseRateUpdateFrequency;
	//Relationships
	private Rate baseRate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getLoanTypeProfileID() {
		return loanTypeProfileID;
	}
	public void setLoanTypeProfileID(Long loanTypeProfileID) {
		this.loanTypeProfileID = loanTypeProfileID;
	}
	@ManyToOne
	@JoinColumn( name = "LoanTypeID" )
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	public Date getEffectiveDate() {
		return (effectiveDate==null)?null:(Date)effectiveDate.clone();
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = (effectiveDate==null)?null:(Date)effectiveDate.clone();
	}
	public Date getEndDate() {
		return (endDate==null)?null:(Date)endDate.clone();
	}
	public void setEndDate(Date endDate) {
		this.endDate = (endDate==null)?null:(Date)endDate.clone();
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
		return this.getLoanTypeProfileID();
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((baseRate == null) ? 0 : baseRate.hashCode());
		result = prime
				* result
				+ ((baseRateUpdateFrequency == null) ? 0
						: baseRateUpdateFrequency.hashCode());
		result = prime
				* result
				+ ((daysBeforeDueToBill == null) ? 0 : daysBeforeDueToBill
						.hashCode());
		result = prime * result
				+ ((daysLateForFee == null) ? 0 : daysLateForFee.hashCode());
		result = prime * result
				+ ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((graceMonths == null) ? 0 : graceMonths.hashCode());
		result = prime * result
				+ ((isVariableRate == null) ? 0 : isVariableRate.hashCode());
		result = prime * result
				+ ((lateFeeAmount == null) ? 0 : lateFeeAmount.hashCode());
		result = prime * result
				+ ((loanType == null) ? 0 : loanType.hashCode());
		result = prime
				* result
				+ ((loanTypeProfileID == null) ? 0 : loanTypeProfileID
						.hashCode());
		result = prime * result
				+ ((maximumLoanTerm == null) ? 0 : maximumLoanTerm.hashCode());
		result = prime
				* result
				+ ((minDaysToFirstDue == null) ? 0 : minDaysToFirstDue
						.hashCode());
		result = prime * result
				+ ((prepaymentDays == null) ? 0 : prepaymentDays.hashCode());
		result = prime
				* result
				+ ((repaymentStartType == null) ? 0 : repaymentStartType
						.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanTypeProfile other = (LoanTypeProfile) obj;
		if (baseRate == null) {
			if (other.baseRate != null)
				return false;
		} else if (!baseRate.equals(other.baseRate))
			return false;
		if (baseRateUpdateFrequency != other.baseRateUpdateFrequency)
			return false;
		if (daysBeforeDueToBill == null) {
			if (other.daysBeforeDueToBill != null)
				return false;
		} else if (!daysBeforeDueToBill.equals(other.daysBeforeDueToBill))
			return false;
		if (daysLateForFee == null) {
			if (other.daysLateForFee != null)
				return false;
		} else if (!daysLateForFee.equals(other.daysLateForFee))
			return false;
		if (effectiveDate == null) {
			if (other.effectiveDate != null)
				return false;
		} else if (!effectiveDate.equals(other.effectiveDate))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (graceMonths == null) {
			if (other.graceMonths != null)
				return false;
		} else if (!graceMonths.equals(other.graceMonths))
			return false;
		if (isVariableRate == null) {
			if (other.isVariableRate != null)
				return false;
		} else if (!isVariableRate.equals(other.isVariableRate))
			return false;
		if (lateFeeAmount == null) {
			if (other.lateFeeAmount != null)
				return false;
		} else if (!lateFeeAmount.equals(other.lateFeeAmount))
			return false;
		if (loanType == null) {
			if (other.loanType != null)
				return false;
		} else if (!loanType.equals(other.loanType))
			return false;
		if (loanTypeProfileID == null) {
			if (other.loanTypeProfileID != null)
				return false;
		} else if (!loanTypeProfileID.equals(other.loanTypeProfileID))
			return false;
		if (maximumLoanTerm == null) {
			if (other.maximumLoanTerm != null)
				return false;
		} else if (!maximumLoanTerm.equals(other.maximumLoanTerm))
			return false;
		if (minDaysToFirstDue == null) {
			if (other.minDaysToFirstDue != null)
				return false;
		} else if (!minDaysToFirstDue.equals(other.minDaysToFirstDue))
			return false;
		if (prepaymentDays == null) {
			if (other.prepaymentDays != null)
				return false;
		} else if (!prepaymentDays.equals(other.prepaymentDays))
			return false;
		if (repaymentStartType != other.repaymentStartType)
			return false;
		return true;
	}
}
