package org.gsoft.openserv.domain.payment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.OpenServDomainObject;
import org.gsoft.openserv.domain.loan.LoanEvent;

@Entity
public class LateFee extends OpenServDomainObject{
	private static final long serialVersionUID = 8734376381093496118L;
	private Long lateFeeID;
	private Integer feeAmount;
	//Relationships
	private BillingStatement billingStatement;
	private LoanEvent loanEvent;
	private LoanEvent cancelledLoanEvent;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getLateFeeID() {
		return lateFeeID;
	}
	public void setLateFeeID(Long lateFeeID) {
		this.lateFeeID = lateFeeID;
	}
	public Integer getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(Integer feeAmount) {
		this.feeAmount = feeAmount;
	}
	@OneToOne
    @JoinColumn(name="BillingStatementID")
	public BillingStatement getBillingStatement() {
		return billingStatement;
	}
	public void setBillingStatement(BillingStatement billingStatement) {
		this.billingStatement = billingStatement;
	}
	@OneToOne
    @JoinColumn(name="LoanEventID")
	public LoanEvent getLoanEvent() {
		return loanEvent;
	}
	public void setLoanEvent(LoanEvent loanEvent) {
		this.loanEvent = loanEvent;
	}
	@OneToOne
    @JoinColumn(name="CancelledLoanEventID")
	public LoanEvent getCancelledLoanEvent() {
		return cancelledLoanEvent;
	}
	public void setCancelledLoanEvent(LoanEvent loanEvent) {
		this.cancelledLoanEvent = loanEvent;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getLateFeeID();
	}
	
	@Transient
	public boolean isCancelled(){
		return this.getCancelledLoanEvent() != null;
	}
}
