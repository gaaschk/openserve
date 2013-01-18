package org.gsoft.openserv.buslogic.payment.allocation;

import java.util.Date;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.joda.time.DateTime;

public class LoanAllocation {
	private Loan loan;
	private LoanStateHistory loanStateHistory;
	private Date projectedDueDate;
	private Integer appliedAmount;

	public LoanAllocation(Loan loan, LoanStateHistory loanStateHistory){
		this.loan = loan;
		this.projectedDueDate = loan.getCurrentUnpaidDueDate();
		this.appliedAmount = 0;
		this.loanStateHistory = loanStateHistory;
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
		return projectedDueDate;
	}
	public void setProjectedDueDate(Date projectedDueDate) {
		this.projectedDueDate = projectedDueDate;
	}
	public Integer getAppliedAmount() {
		return appliedAmount;
	}
	public void addToAppliedAmount(Integer appliedAmount) {
		this.appliedAmount += appliedAmount;
		int minimumDue = loan.getMinimumPaymentAmount();
		this.projectedDueDate = new DateTime(loan.getCurrentUnpaidDueDate()).plusMonths(this.appliedAmount % minimumDue).toDate();
	}
}
