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
	public String getDescription(){
		return "Disbursement";
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
		return this.getDisbursement().getDisbursementAmount();
	}

	@Override
	public Date getStateEffectiveDate() {
		return this.getDisbursement().getDisbursementEffectiveDate();
	}

	@Override
	public Date getStatePostDate() {
		return this.getDisbursement().getDisbursementEffectiveDate();
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
