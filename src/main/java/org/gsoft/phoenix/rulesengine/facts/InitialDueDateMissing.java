package org.gsoft.phoenix.rulesengine.facts;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.rulesengine.AbstractFact;


public class InitialDueDateMissing extends AbstractFact{
	private Loan loan = null;
	
	public InitialDueDateMissing(Loan loan){
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
		return this.getLoan().getInitialDueDate() == null;
	}
	
}
