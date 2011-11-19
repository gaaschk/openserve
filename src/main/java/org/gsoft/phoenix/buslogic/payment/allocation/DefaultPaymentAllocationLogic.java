package org.gsoft.phoenix.buslogic.payment.allocation;

import java.util.List;

import org.gsoft.phoenix.buslogic.payment.PaymentAllocationLogic;
import org.gsoft.phoenix.domain.Loan;
import org.gsoft.phoenix.domain.payment.LoanPayment;
import org.gsoft.phoenix.domain.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public class DefaultPaymentAllocationLogic implements PaymentAllocationLogic{
	@Override
	public void allocatePayment(Payment payment, List<Loan> loans) {
		int amountToAllocate = payment.getPaymentAmount()/loans.size();
		int remainingAmount = payment.getPaymentAmount();
		for(Loan loan:loans){
			LoanPayment loanPayment = new LoanPayment();
			loanPayment.setAppliedAmount(amountToAllocate);
			loanPayment.setLoanID(loan.getLoanID());
			payment.getLoanPayments().add(loanPayment);
			remainingAmount-=amountToAllocate;
			if(amountToAllocate>remainingAmount){
				loanPayment.setAppliedAmount(loanPayment.getAppliedAmount()+remainingAmount);
				break;
			}
		}
	}

}
