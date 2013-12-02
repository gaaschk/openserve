package org.gsoft.openserv.domain.loan.loanstate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class LoanStateHistory {
	private ArrayList<LoanState> loanStates = null;
	
	public ArrayList<LoanState> getLoanStates(){
		if(loanStates == null){
			loanStates = new ArrayList<LoanState>();
		}
		return loanStates;
	}
	
	private LoanState getLastLoanState(){
		return this.getLoanStates().get(this.getLoanStates().size()-1);
	}
	
	public Integer getEndingPrincipal(){
		return this.getLastLoanState().getPrincipal();
	}
	
	public BigDecimal getEndingInterest(){
		return this.getLastLoanState().getInterest();
	}
	
	public LoanState getLoanStateAsOf(Date asOfDate){
		LoanState lastState = null;
		for(LoanState state:this.getLoanStates()){
			if(!state.getStateEffectiveDate().after(asOfDate)){
				lastState = state;
			}
		}
		return lastState;
	}
	
	public Integer getEndingFees(){
		return this.getLastLoanState().getFees();
	}
	
	public BigDecimal getEndingInterestRate(){
		return this.getLastLoanState().getInterestRate();
	}
	
	public BigDecimal getEndingBaseRate(){
		return this.getLastLoanState().getBaseRate();
	}
	
	public BigDecimal getEndingMargin(){
		return this.getLastLoanState().getMargin();
	}
	
/*
	public String toString(){
		StringBuffer strBuf = new StringBuffer();
		for(LoanState state:this.getLoanStates()){
			strBuf.append("---------------------------------------------").append(System.getProperty("line.separator"));
			strBuf.append(state.toString());
			strBuf.append("---------------------------------------------").append(System.getProperty("line.separator"));
		}
		return strBuf.toString();
	}
*/
}

