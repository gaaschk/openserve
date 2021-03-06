package org.gsoft.openserv.buslogic.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.amortization.AmortizationLogic;
import org.gsoft.openserv.buslogic.repayment.RepaymentStartDateCalculator;
import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgramSettings;
import org.gsoft.openserv.domain.payment.billing.BillingStatement;
import org.gsoft.openserv.repositories.loan.LoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.repositories.payment.BillingStatementRepository;
import org.gsoft.openserv.repositories.payment.LoanPaymentRepository;
import org.gsoft.openserv.rulesengine.event.BillingStatementCreatedEvent;
import org.gsoft.openserv.rulesengine.event.SystemEventHandler;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class BillingStatementLogic {
	@Resource
	private BillingStatementRepository billingStatementRepository;
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private SystemSettingsLogic systemSettings;
	@Resource
	private LoanPaymentRepository loanPaymentRepository;
	@Resource
	private LateFeeLogic lateFeeLogic;
	@Resource
	private SystemEventHandler systemEventHandler;
	@Resource
	private AmortizationLogic amortizationLogic;
	@Resource
	private LoanProgramSettingsRepository loanProgramSettingsRepository;
	@Resource
	private RepaymentStartDateCalculator repaymentStartDateCalculator;
	
	public boolean isBillingStatementNeeded(Loan loan){
		Date systemDate = systemSettings.getCurrentSystemDate();
		LoanProgramSettings loanSettings = loanProgramSettingsRepository.findEffectiveLoanProgramSettingsForLoan(loan);
		Date repaymentStartDate = repaymentStartDateCalculator.calculateEarliestRepaymentStartDate(loan); 
		Date earliestDueBillingDate = new DateTime(repaymentStartDate).plusDays(loanSettings.getMinDaysToFirstDue()).minusDays(loanSettings.getDaysBeforeDueToBill()).toDate();
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
			Date repaymentStartDate = repaymentStartDateCalculator.calculateRepaymentStartDateForAccount(loan.getAccount()); 
			LoanProgramSettings ltpAtRepayStart = loanProgramSettingsRepository.findLoanProgramSettingsForLoanEffectiveOn(loan, repaymentStartDate);
			Date earliestDueBillingDate = new DateTime(repaymentStartDate).plusDays(ltpAtRepayStart.getMinDaysToFirstDue()).minusDays(ltpAtRepayStart.getDaysBeforeDueToBill()).toDate();
			BillingStatement lastStatement = billingStatementRepository.findMostRecentBillingStatementForLoan(loan.getLoanID());
			Date dueDate = null;
			if (lastStatement == null) {
				dueDate = new DateTime(earliestDueBillingDate).plusDays(ltpAtRepayStart.getDaysBeforeDueToBill()).toDate();
			} else {
				dueDate = new DateTime(lastStatement.getDueDate()).plusMonths(1).toDate();
			}
			statement = new BillingStatement();
			statement.setLoanID(loan.getLoanID());
			statement.setCreatedDate(systemDate);
			statement.setDueDate(dueDate);
			statement.setMinimumRequiredPayment(amortizationLogic.findPaymentAmountForDate(loan,dueDate));
			statement = billingStatementRepository.save(statement);
		}
		systemEventHandler.handleEvent(new BillingStatementCreatedEvent(statement));
		return statement;
	}
}
