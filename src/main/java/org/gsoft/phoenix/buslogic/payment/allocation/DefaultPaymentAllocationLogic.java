package org.gsoft.phoenix.buslogic.payment.allocation;

import java.util.Comparator;
import java.util.List;

import org.gsoft.phoenix.buslogic.payment.PaymentAllocationLogic;
import org.gsoft.phoenix.domain.loan.Loan;
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
	
	class DueDateComparator implements Comparator<Loan>{
		public int compare(Loan arg0, Loan arg1) {
			if(arg0.getCurrentUnpaidDueDate() == null && arg1.getCurrentUnpaidDueDate() == null)
				return 0;
			if(arg0.getCurrentUnpaidDueDate()!=null)return arg0.getCurrentUnpaidDueDate().compareTo(arg1.getCurrentUnpaidDueDate());
			return arg1.getCurrentUnpaidDueDate().compareTo(arg0.getCurrentUnpaidDueDate())*-1;
		}
	}
}
