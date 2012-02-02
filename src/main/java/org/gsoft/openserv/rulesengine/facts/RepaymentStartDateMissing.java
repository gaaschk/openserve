package org.gsoft.openserv.rulesengine.facts;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.rulesengine.AbstractFact;


public class RepaymentStartDateMissing extends AbstractFact{
	private Loan loan = null;
	
	public RepaymentStartDateMissing(Loan loan){
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
		return this.getLoan().getRepaymentStartDate() == null;
	}
	
}
