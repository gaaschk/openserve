package org.gsoft.openserv.buslogic.payment.allocation;

import java.util.Date;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.billing.BillingStatement;
import org.gsoft.openserv.domain.payment.billing.LoanStatementSummary;

public class LoanAllocation {
	private Loan loan;
	private LoanStateHistory loanStateHistory;
	private LoanStatementSummary statementSummary;
	private LoanPayment newLoanPayment;
	
	public LoanAllocation(Loan loan, LoanStateHistory loanStateHistory, LoanStatementSummary statementSummary, LoanPayment newLoanPayment){
		this.loan = loan;
		this.statementSummary = statementSummary;
		this.newLoanPayment = newLoanPayment;
		this.loanStateHistory = loanStateHistory;
		loanStateHistory.addPayment(newLoanPayment);
		statementSummary.addPayment(newLoanPayment);
		statementSummary.applyPayments();
	}
	
	public Loan getLoan() {
		return loan;
	}
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	public LoanStateHistory getLoanStateHistory() {
		return loanStateHistory;
	}
	public void setLoanStateHistory(LoanStateHistory loanStateHistory) {
		this.loanStateHistory = loanStateHistory;
	}

	public Date getProjectedDueDate() {
		return this.statementSummary.getEarliestUnpaidDueDate();
	}
	
	public Integer getMinimumPaymentAmount() {
		Integer minReq = this.statementSummary.getMinimumPaymentToAdvanceDueDate();
		if(minReq == null){
			minReq = this.getLoanStateHistory().getEndingPrincipal();
		}
		return minReq;
	}
	
	public void addToAppliedAmount(Integer appliedAmount) {
		newLoanPayment.setAppliedAmount(newLoanPayment.getAppliedAmount()+appliedAmount);
		statementSummary.applyPayments();
	}
}
