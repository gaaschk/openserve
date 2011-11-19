package org.gsoft.phoenix.domain.loan;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

@Entity
public class LoanTransaction implements PhoenixDomainObject{
	private static final long serialVersionUID = 5151555488279150572L;
	private Long loanTransactionID;
	private BigDecimal interestAccrued = new BigDecimal(0);
	private BigDecimal interestPaid = new BigDecimal(0);
	private Integer principalChange = 0;
	private BigDecimal interestChange = new BigDecimal(0);
	private Integer feesChange = 0;
	private Integer endingPrincipal = 0;
	private BigDecimal endingInterest = new BigDecimal(0);
	private Integer endingFees = 0;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getLoanTransactionID() {
		return loanTransactionID;
	}
	public void setLoanTransactionID(Long loanTransactionID) {
		this.loanTransactionID = loanTransactionID;
	}
	public BigDecimal getInterestAccrued() {
		return interestAccrued;
	}
	public void setInterestAccrued(BigDecimal interestAccrued) {
		this.interestAccrued = interestAccrued;
	}
	public BigDecimal getInterestPaid() {
		return interestPaid;
	}
	public void setInterestPaid(BigDecimal interestPaid) {
		this.interestPaid = interestPaid;
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
