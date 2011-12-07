package org.gsoft.phoenix.web.controller.accountsummary.model;

import java.math.BigDecimal;
import java.util.Date;

public class LoanEventModel {
	private Long loanID;
	private String loanEventType;
	private Date loanEventEffectiveDate;
	private Date loanEventPostDate;
	private BigDecimal interestAccrued;
	private Integer principalChange;
	private BigDecimal interestChange;
	private Integer feesChange;
	private Integer endingPrincipal;
	private BigDecimal endingInterest;
	private Integer endingFees;
	
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	public String getLoanEventType() {
		return loanEventType;
	}
	public void setLoanEventType(String loanEventType) {
		this.loanEventType = loanEventType;
	}
	public Date getLoanEventEffectiveDate() {
		return loanEventEffectiveDate;
	}
	public void setLoanEventEffectiveDate(Date loanEventEffectiveDate) {
		this.loanEventEffectiveDate = loanEventEffectiveDate;
	}
	public Date getLoanEventPostDate() {
		return loanEventPostDate;
	}
	public void setLoanEventPostDate(Date loanEventPostDate) {
		this.loanEventPostDate = loanEventPostDate;
	}
	public BigDecimal getInterestAccrued() {
		return interestAccrued;
	}
	public void setInterestAccrued(BigDecimal interestAccrued) {
		this.interestAccrued = interestAccrued;
	}
	public Integer getPrincipalChange() {
		return principalChange;
	}
	public void setPrincipalChange(Integer principalChange) {
		this.principalChange = principalChange;
	}
	public BigDecimal getInterestChange() {
		return interestChange;
	}
	public void setInterestChange(BigDecimal interestChange) {
		this.interestChange = interestChange;
	}
	public Integer getFeesChange() {
		return feesChange;
	}
	public void setFeesChange(Integer feesChange) {
		this.feesChange = feesChange;
	}
	public Integer getEndingPrincipal() {
		return endingPrincipal;
	}
	public void setEndingPrincipal(Integer endingPrincipal) {
		this.endingPrincipal = endingPrincipal;
	}
	public BigDecimal getEndingInterest() {
		return endingInterest;
	}
	public void setEndingInterest(BigDecimal endingInterest) {
		this.endingInterest = endingInterest;
	}
	public Integer getEndingFees() {
		return endingFees;
	}
	public void setEndingFees(Integer endingFees) {
		this.endingFees = endingFees;
	}
}
