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
	
	public BillingStatement createBillingStatement(Loan loan){
		LoanTypeProfile ltp = loanTypeProfileRepository.findOne(loan.getEffectiveLoanTypeProfileID());
		BillingStatement lastStatement = billingStatementRepository.findMostRecentBillingStatementForLoan(loan.getLoanID());
		DateTime dueDateMinusWindow = new DateTime(loan.getCurrentUnpaidDueDate()).minusDays(ltp.getDaysBeforeDueToBill());
		DateTime sysDate = new DateTime(systemSettings.getCurrentSystemDate());
		if(!dueDateMinusWindow.isAfter(sysDate) && (lastStatement == null || lastStatement.getDueDate().before(loan.getNextDueDate()))){
			Date dueDate = null;
			if(lastStatement == null)
				dueDate = loan.getCurrentUnpaidDueDate();
			else
				dueDate = new DateTime(lastStatement.getDueDate()).plusMonths(1).toDate();
			BillingStatement statement = new BillingStatement();
			statement.setLoanID(loan.getLoanID());
			statement.setCreatedDate(new Date());
			statement.setDueDate(dueDate);
			statement.setMinimumRequiredPayment(loan.getMinimumPaymentAmount());
			statement = billingStatementRepository.save(statement);
			lastStatement = statement;
		}
		lateFeeLogic.updateLateFees(loan);
		return lastStatement;
	}
	
	/**
	 * In order to ensure that all of the statements are properly re-balanced, 
	 * 
	 * @param the new LoanPayment to be applied
	 */
	public void updateBillingStatementsForLoan(Long loanID, Date startDate){
		Loan theLoan = loanRepository.findOne(loanID);
		LoanTypeProfile ltp = this.loanTypeProfileRepository.findOne(theLoan.getEffectiveLoanTypeProfileID());
		Date effDatePrevMonth = new DateTime(startDate).minusMonths(1).toDate();
		List<BillingStatement> dirtyStatements = billingStatementRepository.findAllBillsForLoanWithPaymentsMadeOnOrAfterOrUnpaidByOrDueAfter(loanID, startDate, startDate, effDatePrevMonth);
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
		lateFeeLogic.updateLateFees(theLoan);
	}
}
