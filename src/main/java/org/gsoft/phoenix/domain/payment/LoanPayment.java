package org.gsoft.phoenix.domain.payment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LoanPayment {
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
}
