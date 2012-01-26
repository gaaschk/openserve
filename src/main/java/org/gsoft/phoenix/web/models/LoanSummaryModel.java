package org.gsoft.phoenix.web.models;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gsoft.phoenix.domain.loan.LoanType;
import org.gsoft.phoenix.util.formatter.CurrencyInPenniesFormat;

public class LoanSummaryModel implements Serializable{
	private static final long serialVersionUID = -6256763851983742148L;
	private Long loanID;
	private LoanType loanType;
	private Integer currentPrincipal;
	private BigDecimal currentInterest;
	private Integer currentFees;
	private boolean selected;
	
	public Long getLoanID(){
		return loanID;
	}
	public void setLoanID(Long loanID){
		this.loanID = loanID;
	}
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
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
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}