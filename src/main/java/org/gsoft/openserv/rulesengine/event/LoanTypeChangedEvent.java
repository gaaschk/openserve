package org.gsoft.openserv.rulesengine.event;

import org.gsoft.openserv.domain.loan.LoanType;

public class LoanTypeChangedEvent implements SystemEvent {
	private LoanType loanType;
	
	public LoanTypeChangedEvent(LoanType loanType){
		this.loanType = loanType; 
	}
	
	public LoanType getLoanType(){
		return loanType;
	}
}
