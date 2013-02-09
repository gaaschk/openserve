package org.gsoft.openserv.buslogic.payment;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.payment.allocation.PastDueFirstPrincipalWeightedAllocationStrategy;
import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.repositories.payment.PaymentRepository;
import org.gsoft.openserv.rulesengine.event.LoanPaymentAppliedEvent;
import org.gsoft.openserv.rulesengine.event.SystemEventHandler;
import org.springframework.stereotype.Component;

@Component
public class PaymentLogic {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private PaymentRepository paymentRepository;
	@Resource(type=PastDueFirstPrincipalWeightedAllocationStrategy.class)
	private PaymentAllocationLogic paymentAllocationLogic;
	@Resource
	private SystemSettingsLogic systemSettingsLogic;
	@Resource
	private BillingStatementLogic statementLogic;
	@Resource
	private SystemEventHandler eventHandler;
	
	public void applyPayment(long borrowerPersonID, int amount, Date effectiveDate){
		Payment payment = new Payment();
		payment.setEffectiveDate(effectiveDate);
		payment.setPostDate(systemSettingsLogic.getCurrentSystemDate());
		payment.setPaymentAmount(amount);
		payment.setBorrowerPersonID(borrowerPersonID);
		
		List<Loan> loans = loanRepository.findAllForBorrowerActiveOnOrBefore(borrowerPersonID, effectiveDate);
		paymentAllocationLogic.allocatePayment(payment, loans);
		payment = paymentRepository.save(payment);
		for(LoanPayment lp:payment.getLoanPayments())
			eventHandler.handleEvent(new LoanPaymentAppliedEvent(lp));
	}
}
