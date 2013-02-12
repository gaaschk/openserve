package org.gsoft.openserv.rulesengine.event;

import org.gsoft.openserv.domain.loan.LoanTypeProfile;

public class LoanTypeProfileChangedEvent implements SystemEvent {
	private LoanTypeProfile loanTypeProfile;
	
	public LoanTypeProfileChangedEvent(LoanTypeProfile loanTypeProfile){
		this.loanTypeProfile = loanTypeProfile; 
	}
	
	public LoanTypeProfile getLoanTypeProfile(){
		return loanTypeProfile;
	}
}
