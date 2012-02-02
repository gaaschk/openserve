package org.gsoft.openserv.buslogic.payment;

import java.util.List;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public interface PaymentAllocationLogic {
	public void allocatePayment(Payment payment, List<Loan> loans);
}
