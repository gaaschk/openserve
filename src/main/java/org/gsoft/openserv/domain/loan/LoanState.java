package org.gsoft.openserv.domain.loan;

import java.math.BigDecimal;
import java.util.Date;

import org.gsoft.openserv.buslogic.interest.InterestCalculator;

public abstract class LoanState {
	private LoanState previousState;
	
	protected LoanState(){}
	
	LoanState getPreviousState(){
		return this.previousState;
	}
	
	public abstract String getDescription();

	public abstract Integer getFeesChange();
	
	public abstract BigDecimal getInterestChange();
	
	public abstract Integer getPrincipalChange();
	
	public final BigDecimal getInterestRate(){
		return this.getBaseRate().add(this.getMargin());
	}
	
	public abstract BigDecimal getBaseRate();
	
	public abstract BigDecimal getMargin();
	
	public abstract Date getStateEffectiveDate();

	public abstract Date getStatePostDate();
	
	public abstract Integer getUnusedPaymentAmount();
	
	public Integer getFees(){
		int previousFees = (this.getPreviousState() == null)?0:this.getPreviousState().getFees();
		return previousFees + this.getFeesChange();
	}
	
	public BigDecimal getAccruedInterest(){
		BigDecimal accruedInterest = BigDecimal.ZERO;
		if(this.getPreviousState() != null){
			accruedInterest = InterestCalculator.calculateInterest(this.getPreviousState().getInterestRate(), this.getPreviousState().getPrincipal(), this.getPreviousState().getStateEffectiveDate(), this.getStateEffectiveDate());
		}
		return accruedInterest;
	}
	
	public BigDecimal getInterest(){
		BigDecimal previousInterest = (this.getPreviousState() == null)?BigDecimal.ZERO:this.getPreviousState().getInterest();
		BigDecimal allInterest = this.getAccruedInterest().add(previousInterest);
		return allInterest.add(this.getInterestChange());
	}
	
	public Integer getPrincipal(){
		int previousPrincipal = (this.getPreviousState() == null)?0:this.getPreviousState().getPrincipal();
		return previousPrincipal + this.getPrincipalChange();
	}
	
	public void setPreviousLoanState(LoanState prevState){
		this.previousState = prevState;
	}
	
	public BigDecimal getInterestThrough(Date asOfDate){
		BigDecimal totalInterest = this.getInterest();
		BigDecimal accruedInterest = InterestCalculator.calculateInterest(this.getInterestRate(), this.getPrincipal(), this.getStateEffectiveDate(), asOfDate);
		return totalInterest.add(accruedInterest);
	}
	
	public String toString(){
		StringBuffer strBuf = new StringBuffer("");
		strBuf.append("Loan State as of ").append(this.getStateEffectiveDate()).append(System.getProperty("line.separator"));
		strBuf.append("Principal: ").append(this.getPrincipal()).append(System.getProperty("line.separator"));
		strBuf.append("Interest: ").append(this.getInterest()).append(System.getProperty("line.separator"));
		strBuf.append("Fees: ").append(this.getFees()).append(System.getProperty("line.separator"));
		strBuf.append("Interest Rate: ").append(this.getInterestRate()).append(System.getProperty("line.separator"));
		return strBuf.toString();
	}
}