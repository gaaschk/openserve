package org.gsoft.phoenix.web.models;

import org.gsoft.phoenix.util.formatter.CurrencyInPenniesFormat;

public class LoanPaymentModel {
	private Long loanID;
	private Integer appliedAmount;
	
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	@CurrencyInPenniesFormat
	public Integer getAppliedAmount() {
		return appliedAmount;
	}
	public void setAppliedAmount(Integer appliedAmount) {
		this.appliedAmount = appliedAmount;
	}
}
