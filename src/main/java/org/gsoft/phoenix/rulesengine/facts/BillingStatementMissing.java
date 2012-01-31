package org.gsoft.phoenix.rulesengine.facts;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.payment.BillingStatement;
import org.gsoft.phoenix.repositories.payment.BillingStatementRepository;
import org.gsoft.phoenix.rulesengine.AbstractFact;
import org.gsoft.phoenix.util.ApplicationContextLocator;
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
		return true;
	}

	@Override
	protected boolean evaluate() {
		ApplicationContext applicationContext = ApplicationContextLocator.getApplicationContext();
		BillingStatementRepository billingStatementRepository = (BillingStatementRepository)applicationContext.getBean(BillingStatementRepository.class);
		BillingStatement lastStatement = billingStatementRepository.findMostRecentBillingStatementForLoan(this.getLoan().getLoanID());
		return (lastStatement == null || lastStatement.getDueDate().before(this.getLoan().getNextDueDate()));
	}
}
