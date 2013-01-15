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
	Integer getFeesChange() {
		return this.getBalanceAdjustment().getFeesChange();
	}

	@Override
	BigDecimal getInterestChange() {
		return BigDecimal.valueOf(this.getBalanceAdjustment().getInterestChange());
	}

	@Override
	Integer getPrincipalChange() {
		return this.getBalanceAdjustment().getPrincipalChange();
	}

	@Override
	BigDecimal getInterestRate() {
		return this.getPreviousState().getInterestRate();
	}

	@Override
	Date getStateEffectiveDate() {
		return this.getBalanceAdjustment().getEffectiveDate();
	}

	@Override
	Date getStatePostDate() {
		return this.getBalanceAdjustment().getPostDate();
	}

	@Override
	public Integer getUnusedPaymentAmount() {
		return this.getPreviousState().getUnusedPaymentAmount();
	}
}
