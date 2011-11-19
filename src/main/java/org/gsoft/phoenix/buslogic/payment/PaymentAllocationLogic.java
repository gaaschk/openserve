package org.gsoft.phoenix.buslogic.payment;

import java.util.List;

import org.gsoft.phoenix.domain.Loan;
import org.gsoft.phoenix.domain.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public interface PaymentAllocationLogic {
	public void allocatePayment(Payment payment, List<Loan> loans);
}
