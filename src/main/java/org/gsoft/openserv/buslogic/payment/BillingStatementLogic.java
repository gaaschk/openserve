package org.gsoft.openserv.buslogic.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.payment.BillPayment;
import org.gsoft.openserv.domain.payment.BillingStatement;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.gsoft.openserv.repositories.payment.BillPaymentRepository;
import org.gsoft.openserv.repositories.payment.BillingStatementRepository;
import org.gsoft.openserv.repositories.payment.LoanPaymentRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class BillingStatementLogic {
	@Resource
	private BillingStatementRepository billingStatementRepository;
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private SystemSettingsLogic systemSettings;
	@Resource
	private BillPaymentRepository billPaymentRepository;
	@Resource
	private LoanPaymentRepository loanPaymentRepository;
	@Resource
	private LateFeeLogic lateFeeLogic;
	
	public boolean isBillingStatementNeeded(Loan loan){
		Date systemDate = systemSettings.getCurrentSystemDate();
		LoanTypeProfile ltp = loan.getEffectiveLoanTypeProfile();
		Date repaymentStartDate = loan.getRepaymentStartDate();
		Date earliestDueBillingDate = new DateTime(repaymentStartDate).plusDays(ltp.getMinDaysToFirstDue()).minusDays(ltp.getDaysBeforeDueToBill()).toDate();
		if(!earliestDueBillingDate.after(systemDate)){
			BillingStatement lastStatement = billingStatementRepository.findMostRecentBillingStatementForLoan(loan.getLoanID());
			if(lastStatement == null || !new DateTime(lastStatement.getDueDate()).plusMonths(1).toDate().after(systemDate)){
				return true;
			}		
		}
		return false;
	}
	
	public List<BillingStatement> createBillingStatements(Loan loan){
		ArrayList<BillingStatement> statements = new ArrayList<>();
		BillingStatement statement = null;
		do{
			statement = this.createBillingStatement(loan);
			if(statement != null){
				statements.add(statement);
			}
		}
		while(statement != null);
		return statements;
	}
	
	public BillingStatement createBillingStatement(Loan loan){
		BillingStatement statement = null;
		if(this.isBillingStatementNeeded(loan)){
			Date systemDate = systemSettings.getCurrentSystemDate();
			LoanTypeProfile ltp = loan.getEffectiveLoanTypeProfile();
			Date repaymentStartDate = loan.getRepaymentStartDate();
			Date earliestDueBillingDate = new DateTime(repaymentStartDate).plusDays(ltp.getMinDaysToFirstDue()).minusDays(ltp.getDaysBeforeDueToBill()).toDate();
			BillingStatement lastStatement = billingStatementRepository.findMostRecentBillingStatementForLoan(loan.getLoanID());
			statement = new BillingStatement();
			statement.setLoanID(loan.getLoanID());
			statement.setCreatedDate(systemDate);
			Date dueDate = null;
			if (lastStatement == null) {
				dueDate = new DateTime(earliestDueBillingDate).plusDays(ltp.getDaysBeforeDueToBill()).toDate();
			} else {
				dueDate = new DateTime(lastStatement.getDueDate()).plusMonths(1).toDate();
			}
			statement.setDueDate(dueDate);
			statement.setMinimumRequiredPayment(loan.getMinimumPaymentAmountAsOf(dueDate));
			statement = billingStatementRepository.save(statement);
		}
		lateFeeLogic.updateLateFees(loan);
		return statement;
	}
	
	/**
	 * In order to ensure that all of the statements are properly re-balanced, 
	 * 
	 * @param the new LoanPayment to be applied
	 */
	public void updateBillingStatementsForLoan(Loan loan, Date startDate){
		LoanTypeProfile ltp = loan.getEffectiveLoanTypeProfile();
		Date effDatePrevMonth = new DateTime(startDate).minusMonths(1).toDate();
		List<BillingStatement> dirtyStatements = billingStatementRepository.findAllBillsForLoanWithPaymentsMadeOnOrAfterOrUnpaidByOrDueAfter(loan.getLoanID(), startDate, startDate, effDatePrevMonth);
		List<LoanPayment> dirtyPayments = loanPaymentRepository.findAllLoanPaymentsEffectiveOnOrAfter(startDate);
		Stack<BillingStatement> billStack = new Stack<BillingStatement>();
		for(BillingStatement statement:dirtyStatements){
			ArrayList<BillPayment> paymentsToRemove = new ArrayList<BillPayment>();
			for(BillPayment billPayment:statement.getBillPayments()){
				if(!billPayment.getLoanPayment().getPayment().getEffectiveDate().before(startDate)){
					paymentsToRemove.add(billPayment);
				}
			}
			for(BillPayment paymentToRemove:paymentsToRemove){
				statement.removePayment(paymentToRemove);
				billPaymentRepository.delete(paymentToRemove);
			}
			billStack.push(statement);
		}
		Stack<LoanPayment> paymentStack = new Stack<LoanPayment>();
		for(LoanPayment dirtyPayment:dirtyPayments){
			paymentStack.push(dirtyPayment);
		}
		int remainingPaymentAmountToApply = 0;
		while(!billStack.empty()&&!paymentStack.empty()){
			BillingStatement currentStatement = billStack.pop();
			Date nextStatementPrepaymentWindow = (billStack.empty())?null:new DateTime(billStack.peek().getDueDate()).minusDays(ltp.getPrepaymentDays()).toDate();
			Date currentStatementPrepaymentWindow = new DateTime(currentStatement.getDueDate()).minusDays(ltp.getPrepaymentDays()).toDate();
			while(!paymentStack.empty()){
				LoanPayment currentPayment = paymentStack.peek();
				if(currentStatement.getUnpaidBalance() <= 0 && 
						!currentPayment.getPayment().getEffectiveDate().before(nextStatementPrepaymentWindow)){
					break;
				}
				if(remainingPaymentAmountToApply <= 0){
					remainingPaymentAmountToApply = currentPayment.getAppliedAmount();
				}
				if(!currentPayment.getPayment().getEffectiveDate().before(currentStatementPrepaymentWindow)){
					int amountToApply = 0;
					if(currentStatement.getUnpaidBalance()>remainingPaymentAmountToApply||
							nextStatementPrepaymentWindow==null||
							nextStatementPrepaymentWindow.after(currentPayment.getPayment().getEffectiveDate())){
						amountToApply += remainingPaymentAmountToApply;
					}
					else{
						amountToApply += currentStatement.getUnpaidBalance();
					}
					BillPayment newPayment = currentStatement.addPayment(currentPayment, amountToApply);
					billPaymentRepository.save(newPayment);
					remainingPaymentAmountToApply -= amountToApply;
					if(remainingPaymentAmountToApply <= 0){
						paymentStack.pop();
					}
				}
			}
		}
		lateFeeLogic.updateLateFees(loan);
	}
}
