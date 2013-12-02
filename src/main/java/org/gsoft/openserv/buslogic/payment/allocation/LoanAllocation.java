package org.gsoft.openserv.buslogic.payment.allocation;

import java.util.Date;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.loanstate.LoanStateHistory;
import org.gsoft.openserv.domain.loan.loanstate.LoanStateHistoryBuilder;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.billing.LoanStatementSummary;
import org.gsoft.openserv.domain.payment.billing.StatementPaySummary;

public class LoanAllocation {
	private Loan loan;
	private LoanStateHistoryBuilder loanStateHistoryBuilder;
	private LoanStatementSummary statementSummary;
	private LoanPayment newLoanPayment;
	
	public LoanAllocation(Loan loan, LoanStateHistory loanStateHistory, LoanStatementSummary statementSummary, LoanPayment newLoanPayment){
		this.loan = loan;
		this.statementSummary = statementSummary;
		this.newLoanPayment = newLoanPayment;
		this.loanStateHistoryBuilder = new LoanStateHistoryBuilder(loanStateHistory);
		loanStateHistoryBuilder.addPayment(newLoanPayment);
		statementSummary.addPayment(newLoanPayment);
		statementSummary.applyPayments();
	}
	
	public Loan getLoan() {
		return loan;
	}
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	public LoanStateHistoryBuilder getLoanStateHistoryBuilder() {
		return loanStateHistoryBuilder;
	}
	public void setLoanStateHistoryBuilder(LoanStateHistoryBuilder loanStateHistoryBuilder) {
		this.loanStateHistoryBuilder = loanStateHistoryBuilder;
	}

	public Date getProjectedPrepayDate() {
		StatementPaySummary stmt = this.statementSummary.getEarliestUnpaidByDate(newLoanPayment.getPayment().getEffectiveDate());
		if(stmt == null)
			return newLoanPayment.getPayment().getEffectiveDate();
		return stmt.getPrepayDate();
	}
	
	public Integer getMinimumPaymentAmount() {
		Integer minReq = this.statementSummary.getMinimumPaymentToAdvanceDueDate();
		if(minReq == null){
			minReq = this.getLoanStateHistoryBuilder().getLoanStateHistory().getEndingPrincipal();
		}
		return minReq;
	}
	
	public void addToAppliedAmount(Integer appliedAmount) {
		newLoanPayment.setAppliedAmount(newLoanPayment.getAppliedAmount()+appliedAmount);
		statementSummary.applyPayments();
	}
}
