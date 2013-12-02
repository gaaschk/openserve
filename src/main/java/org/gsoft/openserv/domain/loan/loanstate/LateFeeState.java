package org.gsoft.openserv.domain.loan.loanstate;

import java.math.BigDecimal;
import java.util.Date;

import org.gsoft.openserv.domain.payment.LateFee;

public class LateFeeState extends LoanState {
	private LateFee lateFee;
	
	public LateFeeState(LateFee lateFee){
		this.lateFee = lateFee;
	}
	
	@Override
	public String getDescription(){
		return "Late Fee Assessed";
	}
	
	@Override
	public Integer getFeesChange() {
		return lateFee.getFeeAmount();
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

	@Override
	public Date getStateEffectiveDate() {
		return lateFee.getEffectiveDate();
	}

	@Override
	public Date getStatePostDate() {
		return lateFee.getPostedDate();
	}

	@Override
	public Integer getUnusedPaymentAmount() {
		if(this.getPreviousState() == null){
			return 0;
		}
		return this.getPreviousState().getUnusedPaymentAmount();
	}

}
