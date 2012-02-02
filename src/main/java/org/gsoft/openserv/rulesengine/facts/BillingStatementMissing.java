package org.gsoft.openserv.rulesengine.facts;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.payment.BillingStatement;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.gsoft.openserv.repositories.payment.BillingStatementRepository;
import org.gsoft.openserv.rulesengine.AbstractFact;
import org.gsoft.openserv.util.ApplicationContextLocator;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;

public class BillingStatementMissing extends AbstractFact{
	private Loan loan = null;
	
	public BillingStatementMissing(Loan loan){
		super(loan);
		this.loan = loan;
	}
	
	public Loan getLoan(){
		return loan;
	}

	@Override
	protected void refreshFromContext(){
    	if(this.loan != null){
    		Loan contextLoan = this.findInContext(loan);
    		this.loan = contextLoan;
    	}
	}
	
	@Override
	public boolean isFast() {
		return false;
	}

	@Override
	protected boolean evaluate() {
		ApplicationContext applicationContext = ApplicationContextLocator.getApplicationContext();
		BillingStatementRepository billingStatementRepository = (BillingStatementRepository)applicationContext.getBean(BillingStatementRepository.class);
		BillingStatement lastStatement = billingStatementRepository.findMostRecentBillingStatementForLoan(this.getLoan().getLoanID());
		SystemSettingsLogic systemSettings = applicationContext.getBean(SystemSettingsLogic.class);
		LoanTypeProfile ltp = applicationContext.getBean(LoanTypeProfileRepository.class).findOne(loan.getEffectiveLoanTypeProfileID());
		DateTime dueDateMinusWindow = new DateTime(loan.getCurrentUnpaidDueDate()).minusDays(ltp.getDaysBeforeDueToBill());
		DateTime sysDate = new DateTime(systemSettings.getCurrentSystemDate());
		return (!dueDateMinusWindow.isAfter(sysDate) && (lastStatement == null || lastStatement.getDueDate().before(this.getLoan().getNextDueDate())));
	}
}
