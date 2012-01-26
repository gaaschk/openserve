package org.gsoft.phoenix.web.models;

import java.util.List;


public class AccountSummaryModel {
	private PersonModel borrower;
	private List<LoanSummaryModel> loans;
	private Long selectedLoanID;
	
	public PersonModel getBorrower() {
		return borrower;
	}
	public void setBorrower(PersonModel borrower) {
		this.borrower = borrower;
	}
	public List<LoanSummaryModel> getLoans() {
		return loans;
	}
	public void setLoans(List<LoanSummaryModel> loans) {
		this.loans = loans;
	}
	public Long getSelectedLoanID() {
		return selectedLoanID;
	}
	public void setSelectedLoanID(Long selectedLoanID) {
		this.selectedLoanID = selectedLoanID;
	}
}
