package org.gsoft.openserv.domain.payment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.OpenServDomainObject;

@Entity
public class LoanPayment extends OpenServDomainObject{
	private static final long serialVersionUID = 643268263864731901L;
	private Long loanPaymentID;
	private Long loanID;
	private Long loanBalanceAdjustmentID;
	private Integer appliedAmount;
	//Relationships
	private Payment payment;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getLoanPaymentID() {
		return loanPaymentID;
	}
	public void setLoanPaymentID(Long loanPaymentID) {
		this.loanPaymentID = loanPaymentID;
	}
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	public Long getLoanBalanceAdjustmentID() {
		return loanBalanceAdjustmentID;
	}
	public void setLoanBalanceAdjustmentID(Long loanBalanceAdjustmentID) {
		this.loanBalanceAdjustmentID = loanBalanceAdjustmentID;
	}
	public Integer getAppliedAmount() {
		return appliedAmount;
	}
	public void setAppliedAmount(Integer appliedAmount) {
		this.appliedAmount = appliedAmount;
	}
	@ManyToOne
	@JoinColumn(name="PaymentID")
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getLoanPaymentID();
	}
}
