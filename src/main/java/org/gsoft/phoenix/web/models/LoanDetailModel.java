package org.gsoft.phoenix.web.models;

import java.util.List;

public class LoanDetailModel {
	private LoanFinancialDataModel loanFinancialData;
	private List<LoanEventModel> loanHistory;
	
	public LoanFinancialDataModel getLoanFinancialData() {
		return loanFinancialData;
	}
	public void setLoanFinancialData(LoanFinancialDataModel loanFinancialData) {
		this.loanFinancialData = loanFinancialData;
	}
	public List<LoanEventModel> getLoanHistory() {
		return loanHistory;
	}
	public void setLoanHistory(List<LoanEventModel> loanHistory) {
		this.loanHistory = loanHistory;
	}
}
