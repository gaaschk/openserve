package org.gsoft.openserv.domain.loan;

import java.math.BigDecimal;
import java.util.Date;

import org.gsoft.openserv.domain.payment.LoanPayment;

public class PaymentLoanState extends LoanState {
	private LoanPayment payment;
	private Integer amountToFees;
	private BigDecimal amountToInterest;
	private Integer amountToPrincipal;

	private LoanPayment getPayment(){
		return payment;
	}
	
	private void clearAllocations(){
		this.amountToFees = null;
		this.amountToInterest = null;
		this.amountToPrincipal = null;
	}
	
	private void resetAllocations(){
		int paymentAmount = this.getPayment().getAppliedAmount();
		this.amountToFees = this.allocate(this.getPreviousState().getFees(), paymentAmount);
		paymentAmount -= amountToFees;
		BigDecimal interestAmount = this.getPreviousState().getInterest().add(this.getAccruedInterest());
		this.amountToInterest = BigDecimal.valueOf(this.allocate(interestAmount.intValue(), paymentAmount));
		paymentAmount -= this.amountToInterest.intValue();
		this.amountToPrincipal = this.allocate(this.getPreviousState().getPrincipal(), paymentAmount);
	}
	
	private int allocate(int balance, int paymentAmount){
		if(-1*paymentAmount > balance){
			return -1*balance;
		}
		else{
			return paymentAmount;
		}
	}
	
	public PaymentLoanState(LoanPayment payment){
		this.payment = payment;
	}
	
	@Override
	public void setPreviousLoanState(LoanState prevState) {
		super.setPreviousLoanState(prevState);
		this.clearAllocations();
	}
	
	@Override
	Integer getFeesChange() {
		if(this.amountToFees == null){
			this.resetAllocations();
		}
		return this.amountToFees;
	}

	@Override
	BigDecimal getInterestChange() {
		if(this.amountToInterest == null){
			this.resetAllocations();
		}
		return this.amountToInterest;
	}

	@Override
	Integer getPrincipalChange() {
		if(this.amountToPrincipal == null){
			this.resetAllocations();
		}
		return this.amountToPrincipal;
	}

	@Override
	BigDecimal getInterestRate() {
		return this.getPreviousState().getInterestRate();
	}

	@Override
	Date getStateEffectiveDate() {
		return this.getPayment().getPayment().getEffectiveDate();
	}

	@Override
	Date getStatePostDate() {
		return this.getPayment().getPayment().getPostDate();
	}

	@Override
	public Integer getUnusedPaymentAmount() {
		return this.getPreviousState().getUnusedPaymentAmount();
	}


}
