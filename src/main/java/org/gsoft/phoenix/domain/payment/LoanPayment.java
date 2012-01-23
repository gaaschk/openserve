package org.gsoft.phoenix.domain.payment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

@Entity
public class LoanPayment extends PhoenixDomainObject{
	private static final long serialVersionUID = 643268263864731901L;
	private Long loanPaymentID;
	private Long loanID;
	private Integer appliedAmount;
	
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
	public Integer getAppliedAmount() {
		return appliedAmount;
	}
	public void setAppliedAmount(Integer appliedAmount) {
		this.appliedAmount = appliedAmount;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getLoanPaymentID();
	}
}
