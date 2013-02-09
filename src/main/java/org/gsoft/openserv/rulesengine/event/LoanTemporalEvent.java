package org.gsoft.openserv.rulesengine.event;

import org.gsoft.openserv.domain.loan.Loan;

public class LoanTemporalEvent implements SystemEvent {
	private Loan loan;
	
	public LoanTemporalEvent(Loan loan){
		this.loan = loan;
	}
	
	public Loan getLoan(){
		return loan;
	}
}
