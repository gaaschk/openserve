package org.gsoft.openserv.domain.loan;

import java.math.BigDecimal;
import java.util.Date;

import org.gsoft.openserv.buslogic.interest.InterestCalculator;

public abstract class LoanState {
	private LoanState previousState;
	
	public static LoanState ZERO_STATE = new LoanState(){
		public Integer getPrincipal(){
			return 0;
		}
		
		public BigDecimal getInterest(){
			return BigDecimal.ZERO;
		}
		
		public Integer getFees(){
			return 0;
		}
		
		public BigDecimal getInterestRate(){
			return BigDecimal.ZERO;
		}
		
		@Override
		Integer getFeesChange() {
			return 0;
		}

		@Override
		BigDecimal getInterestChange() {
			return BigDecimal.ZERO;
		}

		@Override
		Integer getPrincipalChange() {
			return 0;
		}

		@Override
		Date getStateEffectiveDate() {
			return null;
		}

		@Override
		Date getStatePostDate() {
			return null;
		}

		@Override
		public Integer getUnusedPaymentAmount() {
			return 0;
		}
	};
	
	protected LoanState(){}
	
	LoanState getPreviousState(){
		return this.previousState;
	}

	abstract Integer getFeesChange();
	
	abstract BigDecimal getInterestChange();
	
	abstract Integer getPrincipalChange();
	
	abstract BigDecimal getInterestRate();
	
	abstract Date getStateEffectiveDate();

	abstract Date getStatePostDate();
	
	public abstract Integer getUnusedPaymentAmount();
	
	public Integer getFees(){
		return this.getPreviousState().getFees() + this.getFeesChange();
	}
	
	BigDecimal getAccruedInterest(){
		BigDecimal accruedInterest = InterestCalculator.calculateInterest(this.getPreviousState().getInterestRate(), this.getPreviousState().getPrincipal(), this.getPreviousState().getStateEffectiveDate(), this.getStateEffectiveDate());
		return accruedInterest;
	}
	
	public BigDecimal getInterest(){
		BigDecimal allInterest = this.getAccruedInterest().add(this.getPreviousState().getInterest());
		return allInterest.add(this.getInterestChange());
	}
	
	public Integer getPrincipal(){
		return this.getPreviousState().getPrincipal() + this.getPrincipalChange();
	}
	
	public void setPreviousLoanState(LoanState prevState){
		this.previousState = prevState;
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