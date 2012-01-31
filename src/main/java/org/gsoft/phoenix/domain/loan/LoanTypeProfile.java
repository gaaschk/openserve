package org.gsoft.phoenix.domain.loan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.phoenix.domain.PhoenixDomainObject;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
public class LoanTypeProfile extends PhoenixDomainObject{
	private static final long serialVersionUID = 6245557328161056820L;
	private Long loanTypeProfileID;
	private Date effectiveDate;
	private Date endDate;
	private Integer maximumLoanTerm;
	private Integer graceMonths;
	private Integer minDaysToFirstDue;
	private Integer prepaymentDays;
	private Integer daysBeforeDueToBill;
	//Enumerations
	private LoanType loanType;
	private RepaymentStartType repaymentStartType;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getLoanTypeProfileID() {
		return loanTypeProfileID;
	}
	public void setLoanTypeProfileID(Long loanTypeProfileID) {
		this.loanTypeProfileID = loanTypeProfileID;
	}
	@Type( type = "org.gsoft.phoenix.util.jpa.GenericEnumUserType", parameters={
			@Parameter(name = "enumClass", value = "org.gsoft.phoenix.domain.loan.LoanType")
	})
    @Column( name = "LoanTypeID" )
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	@Override
	@Transient
	public Long getID() {
		return this.getLoanTypeProfileID();
	}
	@Type( type = "org.gsoft.phoenix.util.jpa.GenericEnumUserType", parameters={
			@Parameter(name = "enumClass", value = "org.gsoft.phoenix.domain.loan.RepaymentStartType")
	})
    @Column( name = "RepaymentStartTypeID" )
	public RepaymentStartType getRepaymentStartType() {
		return repaymentStartType;
	}
	public void setRepaymentStartType(RepaymentStartType repaymentStartType) {
		this.repaymentStartType = repaymentStartType;
	}
}
