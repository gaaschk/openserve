package org.gsoft.openserv.domain.payment.billing;

import org.gsoft.openserv.domain.payment.LoanPayment;

public class StatementPayment {
	private LoanPayment payment;
	private int appliedAmount;
	
	public StatementPayment(LoanPayment pmt, int appliedAmount){
		this.payment = pmt;
		this.appliedAmount = appliedAmount;
	}
	
	public LoanPayment getPayment() {
		return payment;
	}
	public void setPayment(LoanPayment payment) {
		this.payment = payment;
	}
	public int getAppliedAmount() {
		return appliedAmount;
	}
	public void setAppliedAmount(int appliedAmount) {
		this.appliedAmount = appliedAmount;
	}
}
