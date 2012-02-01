package org.gsoft.phoenix.web.models;

import java.util.ArrayList;
import java.util.List;

public class LoanDetailModel {
	private LoanFinancialDataModel loanFinancialData;
	private LoanAmortizationModel currentAmortization;
	private List<LoanEventModel> loanHistory;
	private List<BillingStatementModel> billingStatements;
	
	public LoanFinancialDataModel getLoanFinancialData() {
		return loanFinancialData;
	}
	public void setLoanFinancialData(LoanFinancialDataModel loanFinancialData) {
		this.loanFinancialData = loanFinancialData;
	}
	public LoanAmortizationModel getCurrentAmortization() {
		return currentAmortization;
	}
	public void setCurrentAmortization(LoanAmortizationModel currentAmortization) {
		this.currentAmortization = currentAmortization;
	}
	public List<LoanEventModel> getLoanHistory() {
		return loanHistory;
	}
	public void setLoanHistory(List<LoanEventModel> loanHistory) {
		this.loanHistory = loanHistory;
	}
	public List<BillingStatementModel> getBillingStatements() {
		if(billingStatements == null){
			billingStatements = new ArrayList<BillingStatementModel>();
		}
		return billingStatements;
	}
	public void setBillingStatements(List<BillingStatementModel> billingStatements) {
		this.billingStatements = billingStatements;
	}
}
