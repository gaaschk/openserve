package org.gsoft.openserv.domain.loan;

import java.math.BigDecimal;
import java.util.Date;

import org.gsoft.openserv.buslogic.interest.InterestCalculator;

public class LoanState {
	private LoanState previousState;
	private Date stateEffectiveDate;
	private Date statePostDate;
	private Integer principalChange;
	private BigDecimal interestChange;
	private Integer feesChange;
	private Integer paymentAmount;
	private BigDecimal interestRate;
	
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
	};
	
	private LoanState(){}

	public LoanState(Date stateEffectiveDate, Date statePostDate,
			Integer principalChange, BigDecimal interestChange,
			Integer feesChange, Integer paymentAmount, BigDecimal interestRate) {
		super();
		this.stateEffectiveDate = stateEffectiveDate;
		this.statePostDate = statePostDate;
		this.principalChange = (principalChange == null)?0:principalChange;
		this.interestChange = (interestChange == null)?BigDecimal.ZERO:interestChange;
		this.feesChange = (feesChange == null)?0:feesChange;
		this.paymentAmount = paymentAmount;
		this.interestRate = interestRate;
	}
	
	private LoanState getPreviousState(){
		return this.previousState;
	}
	
	private Integer getFeesChange(){
		return this.feesChange;
	}
	
	private BigDecimal getInterestChange(){
		return this.interestChange;
	}
	
	private Integer getPrincipalChange(){
		return this.principalChange;
	}
	
	private Integer getPaymentAmount(){
		return this.paymentAmount;
	}

	public BigDecimal getInterestRate(){
		if(this.interestRate == null){
			return this.getPreviousState().getInterestRate();
		}
		return this.interestRate;
	}
	
	public Date getStateEffectiveDate(){
		return this.stateEffectiveDate;
	}

	public Date getStatePostDate(){
		return this.statePostDate;
	}
	
	public boolean isPayment(){
		return this.getPaymentAmount() != null;
	}
	
	private Integer getFeesBeforePayment(){
		return this.getPreviousState().getFees() + this.getFeesChange();
	}
	
	public Integer getFees(){
		Integer fees = this.getFeesBeforePayment();
		if(this.isPayment()){
			if(fees > this.getPaymentAmount()){
				fees = fees - this.getPaymentAmount();
			}
			else{
				fees = 0;
			}
		}
		return fees;
	}
	
	private BigDecimal getAccruedInterest(){
		BigDecimal accruedInterest = InterestCalculator.calculateInterest(this.getPreviousState().getInterestRate(), this.getPreviousState().getPrincipal(), this.getPreviousState().getStateEffectiveDate(), this.getStateEffectiveDate());
		return accruedInterest;
	}
	
	public BigDecimal getInterest(){
		BigDecimal allInterest = this.getAccruedInterest().add(this.getPreviousState().getInterest());
		if(this.getInterestChange() != null){
			allInterest = allInterest.add(this.getInterestChange());
		}
		if(this.isPayment() && this.getPaymentAmount() > this.getFeesBeforePayment()){
			BigDecimal paymentAfterFees = BigDecimal.valueOf(this.getPaymentAmount() - this.getFeesBeforePayment());
			int allInterestWhole = allInterest.intValue();
			if(allInterestWhole < paymentAfterFees.intValue()){
				allInterest = allInterest.subtract(BigDecimal.valueOf(allInterestWhole));
			}
			else{
				allInterest = allInterest.subtract(paymentAfterFees);
			}
		}
		return allInterest;
	}
	
	public Integer getPrincipal(){
		if(this.getPaymentAmount() == null){
			return this.getPreviousState().getPrincipal() + this.getPrincipalChange();
		}
		Integer interest = this.getAccruedInterest().add(this.getPreviousState().getInterest()).intValue();
		Integer paymentAfterFeesAndInterest = this.getPaymentAmount() - (interest + this.getPreviousState().getFees());
		if(paymentAfterFeesAndInterest < 0){
			return this.getPreviousState().getPrincipal();
		}
		if(this.getPreviousState().getPrincipal() > paymentAfterFeesAndInterest){
			return this.getPreviousState().getPrincipal() - paymentAfterFeesAndInterest;
		}
		return 0;
	}
	
	public Integer getUnusedPaymentAmount(){
		if(this.getPaymentAmount() != null){
			Integer interest = this.getAccruedInterest().add(this.getPreviousState().getInterest()).intValue();
			Integer paymentAfterPrincipalFeesAndInterest = this.getPaymentAmount() - (interest + this.getPreviousState().getFees() + this.getPreviousState().getPrincipal());
			if(paymentAfterPrincipalFeesAndInterest>0){
				return paymentAfterPrincipalFeesAndInterest;
			}
		}
		return 0;
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