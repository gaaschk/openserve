package org.gsoft.phoenix.buslogic.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.loanevent.LoanEventLogic;
import org.gsoft.phoenix.buslogic.payment.allocation.DefaultPaymentAllocationLogic;
import org.gsoft.phoenix.buslogic.system.SystemSettingsLogic;
import org.gsoft.phoenix.domain.Loan;
import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.domain.loan.LoanEventType;
import org.gsoft.phoenix.domain.loan.LoanTransaction;
import org.gsoft.phoenix.domain.payment.LoanPayment;
import org.gsoft.phoenix.domain.payment.Payment;
import org.gsoft.phoenix.repositories.LoanRepository;
import org.gsoft.phoenix.repositories.payment.PaymentRepository;
import org.springframework.stereotype.Component;

@Component
public class PaymentLogic {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private PaymentRepository paymentRepository;
	@Resource(type=DefaultPaymentAllocationLogic.class)
	private PaymentAllocationLogic paymentAllocationLogic;
	@Resource
	private SystemSettingsLogic systemSettingsLogic;
	@Resource
	private LoanEventLogic loanEventLogic;
	
	public void applyPayment(long borrowerPersonID, int amount, Date effectiveDate){
		Payment payment = new Payment();
		payment.setEffectiveDate(effectiveDate);
		payment.setPostDate(systemSettingsLogic.getCurrentSystemDate());
		payment.setPaymentAmount(amount);
		payment.setBorrowerPersonID(borrowerPersonID);
		
		List<Loan> loans = loanRepository.findAllLoansByBorrowerPersonID(borrowerPersonID);
		paymentAllocationLogic.allocatePayment(payment, loans);
		payment = paymentRepository.save(payment);
		
		this.applyPaymentToLoans(payment);
	}
	
	private void applyPaymentToLoans(Payment payment){
		for(LoanPayment loanPayment:payment.getLoanPayments()){
			Loan theLoan = loanRepository.findOne(loanPayment.getLoanID());
			LoanEvent lastEvent = loanEventLogic.findMostRecentLoanEventWithTransactionEffectivePriorToDate(theLoan.getLoanID(), payment.getEffectiveDate());
			LoanEvent newEvent = loanEventLogic.createLoanEventWithTransaction(theLoan, LoanEventType.PAYMENT_APPLIED, payment.getEffectiveDate());
			int amountToApply = loanPayment.getAppliedAmount();
			int feeAmount = (amountToApply>=lastEvent.getLoanTransaction().getEndingFees())?lastEvent.getLoanTransaction().getEndingFees():amountToApply;
			amountToApply -= feeAmount;
			BigDecimal accruedInterest = lastEvent.getLoanTransaction().getEndingInterest().add(newEvent.getLoanTransaction().getInterestAccrued());
			int interestAmount = (amountToApply>=accruedInterest.intValue())?accruedInterest.intValue():amountToApply;
			amountToApply -= interestAmount;
			int principalAmount = (amountToApply>=lastEvent.getLoanTransaction().getEndingPrincipal())?lastEvent.getLoanTransaction().getEndingPrincipal():amountToApply;
			amountToApply -= principalAmount;
			LoanTransaction newTransaction = newEvent.getLoanTransaction();
			newTransaction.setFeesChange(feeAmount);
			newTransaction.setInterestPaid(new BigDecimal(interestAmount));
			newTransaction.setInterestChange(newTransaction.getInterestAccrued().subtract(newTransaction.getInterestPaid()).multiply(new BigDecimal(-1)));
			newTransaction.setPrincipalChange(principalAmount);
			loanEventLogic.applyLoanEvent(newEvent);
		}
	}
}
