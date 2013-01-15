package org.gsoft.openserv.domain.loan;

import java.math.BigDecimal;
import java.util.Date;

import org.gsoft.openserv.domain.interest.LoanRateValue;

public class RateValueLoanState extends LoanState {
	private LoanRateValue rateValue;
	
	private LoanRateValue getRateValue(){
		return this.rateValue;
	}
	
	public RateValueLoanState(LoanRateValue rateValue){
		this.rateValue = rateValue;
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
	BigDecimal getInterestRate() {
		return this.getRateValue().getRateValue().getRateValue();
	}

	@Override
	Date getStateEffectiveDate() {
		return this.getRateValue().getLockedDate();
	}

	@Override
	Date getStatePostDate() {
		return this.getRateValue().getLockedDate();
	}

	@Override
	public Integer getUnusedPaymentAmount() {
		return this.getPreviousState().getUnusedPaymentAmount();
	}
}
