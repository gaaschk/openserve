package org.gsoft.openserv.rulesengine.event;

import org.gsoft.openserv.domain.payment.LoanPayment;

public class LoanPaymentAppliedEvent implements SystemEvent {
	private LoanPayment payment;
	
	public LoanPaymentAppliedEvent(LoanPayment thePayment){
		this.payment = thePayment;
	}
	
	public LoanPayment getPayment(){
		return this.payment;
	}
}
