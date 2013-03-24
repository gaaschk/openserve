package org.gsoft.openserv.web.accountsummary.model;

import java.io.Serializable;
import java.util.List;

import org.gsoft.openserv.web.person.model.PersonModel;


public class AccountSummaryModel implements Serializable{
	private static final long serialVersionUID = -2998909125562406952L;
	private PersonModel borrower;
	private List<LoanSummaryModel> loans;
	private PaymentHistoryModel paymentHistory;
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
	public PaymentHistoryModel getPaymentHistory() {
		return paymentHistory;
	}
	public void setPaymentHistory(PaymentHistoryModel paymentHistory) {
		this.paymentHistory = paymentHistory;
	}
	public Long getSelectedLoanID() {
		return selectedLoanID;
	}
	public void setSelectedLoanID(Long selectedLoanID) {
		this.selectedLoanID = selectedLoanID;
	}
}
