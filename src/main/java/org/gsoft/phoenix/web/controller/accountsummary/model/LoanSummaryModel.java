package org.gsoft.phoenix.web.controller.accountsummary.model;

import java.math.BigDecimal;

import org.gsoft.phoenix.util.formatter.CurrencyInPenniesFormat;

public class LoanSummaryModel {
	private Long loanID;
	private Integer currentPrincipal;
	private BigDecimal currentInterest;
	private Integer currentFees;
	
	public Long getLoanID(){
		return loanID;
	}
	public void setLoanID(Long loanID){
		this.loanID = loanID;
	}
	@CurrencyInPenniesFormat
	public Integer getCurrentPrincipal() {
		return currentPrincipal;
	}
	public void setCurrentPrincipal(Integer currentPrincipal) {
		this.currentPrincipal = currentPrincipal;
	}
	@CurrencyInPenniesFormat
	public BigDecimal getCurrentInterest() {
		return currentInterest;
	}
	public void setCurrentInterest(BigDecimal currentInterest) {
		this.currentInterest = currentInterest;
	}
	@CurrencyInPenniesFormat
	public Integer getCurrentFees() {
		return currentFees;
	}
	public void setCurrentFees(Integer currentFees) {
		this.currentFees = currentFees;
	}
}
