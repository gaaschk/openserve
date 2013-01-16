package org.gsoft.openserv.domain.loan;

import java.math.BigDecimal;
import java.util.Date;

public class BalanceAdjustmentLoanState extends LoanState {
	private LoanBalanceAdjustment balanceAdjustment;
	
	private LoanBalanceAdjustment getBalanceAdjustment(){
		return balanceAdjustment;
	}
	
	public BalanceAdjustmentLoanState(LoanBalanceAdjustment balanceAdjustment) {
		this.balanceAdjustment = balanceAdjustment;
	}

	@Override
	public Integer getFeesChange() {
		return this.getBalanceAdjustment().getFeesChange();
	}

	@Override
	public BigDecimal getInterestChange() {
		return BigDecimal.valueOf(this.getBalanceAdjustment().getInterestChange());
	}

	@Override
	public Integer getPrincipalChange() {
		return this.getBalanceAdjustment().getPrincipalChange();
	}

	@Override
	public Date getStateEffectiveDate() {
		return this.getBalanceAdjustment().getEffectiveDate();
	}

	@Override
	public Date getStatePostDate() {
		return this.getBalanceAdjustment().getPostDate();
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
