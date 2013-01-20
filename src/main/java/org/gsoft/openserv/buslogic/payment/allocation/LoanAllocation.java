package org.gsoft.openserv.buslogic.payment.allocation;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.gsoft.openserv.domain.payment.BillingStatement;

public class LoanAllocation {
	private Loan loan;
	private LoanStateHistory loanStateHistory;
	private Integer appliedAmount;
	private List<BillingStatement> billingStatements;
	private BillingStatement currentBillingStatement = null;

	public LoanAllocation(Loan loan, LoanStateHistory loanStateHistory, List<BillingStatement> billingStatements){
		this.loan = loan;
		this.billingStatements = billingStatements;
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
	private BillingStatement getCurrentBillingStatement(){
		if(this.currentBillingStatement == null){
			int amountToApply = appliedAmount;
			int index = 0;
			while(amountToApply > 0 && index < billingStatements.size()){
				currentBillingStatement = billingStatements.get(index);
				amountToApply -= currentBillingStatement.getMinimumRequiredPayment();
				index++;
			}
		}
		return currentBillingStatement;
	}
	public Date getProjectedDueDate() {
		Date dueDate = null;
		if(this.getCurrentBillingStatement() != null){
			dueDate = this.getCurrentBillingStatement().getDueDate();
		}
		return dueDate;
	}
	public int getMinimumPaymentAmount() {
		int minAmount = 0;
		if(this.getCurrentBillingStatement() != null){
			minAmount = this.getCurrentBillingStatement().getMinimumRequiredPayment();
		}
		else{
			minAmount = loanStateHistory.getEndingPrincipal();
		}
		return minAmount;
	}
	public Integer getAppliedAmount() {
		return appliedAmount;
	}
	public void addToAppliedAmount(Integer appliedAmount) {
		this.appliedAmount += appliedAmount;
		this.currentBillingStatement = null;
	}
}
