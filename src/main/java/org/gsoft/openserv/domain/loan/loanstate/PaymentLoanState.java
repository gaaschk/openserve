package org.gsoft.openserv.domain.loan.loanstate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.web.support.formatter.currency.CurrencyInPenniesFormatter;

public class PaymentLoanState extends LoanState {
	private LoanPayment payment;
	private Integer amountToFees;
	private BigDecimal amountToInterest;
	private Integer amountToPrincipal;
	private CurrencyInPenniesFormatter currencyFormatter = new CurrencyInPenniesFormatter(0);

	private LoanPayment getPayment(){
		return payment;
	}
	
	private void clearAllocations(){
		this.amountToFees = null;
		this.amountToInterest = null;
		this.amountToPrincipal = null;
	}
	
	private void resetAllocations(){
		if(this.getPreviousState() == null){
			this.amountToFees = 0;
			this.amountToInterest = BigDecimal.ZERO;
			this.amountToPrincipal = 0;
		}
		else{
			int paymentAmount = this.getPayment().getAppliedAmount();
			this.amountToFees = this.allocate(this.getPreviousState().getFees(), paymentAmount);
			paymentAmount -= amountToFees;
			BigDecimal interestAmount = this.getPreviousState().getInterest().add(this.getAccruedInterest());
			this.amountToInterest = BigDecimal.valueOf(this.allocate(interestAmount.intValue(), paymentAmount));
			paymentAmount -= this.amountToInterest.intValue();
			this.amountToPrincipal = this.allocate(this.getPreviousState().getPrincipal(), paymentAmount);
		}
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
	public String getDescription(){
		return "Payment of " + currencyFormatter.print(this.getPayment().getAppliedAmount(), Locale.US) + " Applied";
	}
	
	@Override
	public void setPreviousLoanState(LoanState prevState) {
		super.setPreviousLoanState(prevState);
		this.clearAllocations();
	}
	
	@Override
	public Integer getFeesChange() {
		if(this.amountToFees == null){
			this.resetAllocations();
		}
		return this.amountToFees;
	}

	@Override
	public BigDecimal getInterestChange() {
		if(this.amountToInterest == null){
			this.resetAllocations();
		}
		return this.amountToInterest;
	}

	@Override
	public Integer getPrincipalChange() {
		if(this.amountToPrincipal == null){
			this.resetAllocations();
		}
		return this.amountToPrincipal;
	}

	@Override
	public Date getStateEffectiveDate() {
		return this.getPayment().getPayment().getEffectiveDate();
	}

	@Override
	public Date getStatePostDate() {
		return this.getPayment().getPayment().getPostDate();
	}

	@Override
	public Integer getUnusedPaymentAmount() {
		if(this.getPreviousState() == null){
			return 0;
		}
		return this.getPreviousState().getUnusedPaymentAmount();
	}

	@Override
	public BigDecimal getBaseRate() {
		if(this.getPreviousState() == null){
			return BigDecimal.ZERO;
		}
		return this.getPreviousState().getBaseRate();
	}

	@Override
	public BigDecimal getMargin() {
		if(this.getPreviousState() == null){
			return BigDecimal.ZERO;
		}
		return this.getPreviousState().getMargin();
	}


}
