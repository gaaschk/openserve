package org.gsoft.phoenix.web.controller.accountsummary.model;

import java.util.List;

import org.gsoft.phoenix.web.controller.addloan.model.PersonModel;

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
