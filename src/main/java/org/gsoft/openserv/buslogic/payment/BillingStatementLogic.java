package org.gsoft.openserv.buslogic.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.payment.billing.BillingStatement;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
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
}
