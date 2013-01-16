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
	public Integer getFeesChange() {
		return 0;
	}

	@Override
	public BigDecimal getInterestChange() {
		return BigDecimal.ZERO;
	}

	@Override
	public Integer getPrincipalChange() {
		return 0;
	}

	@Override
	public Date getStateEffectiveDate() {
		return this.getRateValue().getLockedDate();
	}

	@Override
	public Date getStatePostDate() {
		return this.getRateValue().getLockedDate();
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
		return this.getRateValue().getRateValue().getRateValue();
	}

	@Override
	public BigDecimal getMargin() {
		return this.getRateValue().getMarginValue();
	}
}
