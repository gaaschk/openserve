package org.gsoft.openserv.rulesengine.event;

import org.gsoft.openserv.domain.loan.LoanProgram;

public class LoanProgramChangedEvent implements SystemEvent {
	private LoanProgram loanProgram;
	
	public LoanProgramChangedEvent(LoanProgram loanProgram){
		this.loanProgram = loanProgram; 
	}
	
	public LoanProgram getLoanProgram(){
		return loanProgram;
	}
}
