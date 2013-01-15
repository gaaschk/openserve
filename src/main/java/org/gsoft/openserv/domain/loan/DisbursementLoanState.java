package org.gsoft.openserv.domain.loan;

import java.math.BigDecimal;
import java.util.Date;

public class DisbursementLoanState extends LoanState {
	private Disbursement disbursement;
	
	private Disbursement getDisbursement(){
		return disbursement;
	}
	
	public DisbursementLoanState(Disbursement disbursement){
		this.disbursement = disbursement;
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
		return this.getDisbursement().getDisbursementAmount();
	}

	@Override
	BigDecimal getInterestRate() {
		return getPreviousState().getInterestRate();
	}

	@Override
	Date getStateEffectiveDate() {
		return this.getDisbursement().getDisbursementEffectiveDate();
	}

	@Override
	Date getStatePostDate() {
		return this.getDisbursement().getDisbursementEffectiveDate();
	}

	@Override
	public Integer getUnusedPaymentAmount() {
		return this.getPreviousState().getUnusedPaymentAmount();
	}
}
