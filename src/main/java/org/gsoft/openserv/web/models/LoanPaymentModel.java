package org.gsoft.openserv.web.models;

import java.io.Serializable;

import org.gsoft.openserv.web.formatter.currency.CurrencyInPenniesFormat;

public class LoanPaymentModel implements Serializable{
	private static final long serialVersionUID = -1204648670821261004L;
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
