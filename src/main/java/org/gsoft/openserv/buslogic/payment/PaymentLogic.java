package org.gsoft.openserv.buslogic.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.loanevent.LoanEventLogic;
import org.gsoft.openserv.buslogic.payment.allocation.DefaultPaymentAllocationLogic;
import org.gsoft.openserv.buslogic.repayment.NextDueDateCalculator;
import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanEventType;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.predicates.LoanPredicates;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.repositories.payment.PaymentRepository;
import org.gsoft.openserv.util.ListUtility;
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
	@Resource
	private NextDueDateCalculator nextDueCalculator;
	@Resource
	private BillingStatementLogic statementLogic;
	
	public void applyPayment(long borrowerPersonID, int amount, Date effectiveDate){
		Payment payment = new Payment();
		payment.setEffectiveDate(effectiveDate);
		payment.setPostDate(systemSettingsLogic.getCurrentSystemDate());
		payment.setPaymentAmount(amount);
		payment.setBorrowerPersonID(borrowerPersonID);
		
		List<Loan> loans = ListUtility.addAll(new ArrayList<Loan>(), loanRepository.findAll(LoanPredicates.borrowerIdIsAndActiveOnOrBefore(borrowerPersonID, effectiveDate)));
		paymentAllocationLogic.allocatePayment(payment, loans);
		payment = paymentRepository.save(payment);
		this.applyPaymentToLoans(payment);
	}
	
	private void applyPaymentToLoans(Payment payment){
		for(LoanPayment loanPayment:payment.getLoanPayments()){
			Loan theLoan = loanRepository.findOne(loanPayment.getLoanID());
			loanEventLogic.createLoanEvent(theLoan, LoanEventType.PAYMENT_APPLIED, payment.getEffectiveDate(), new BigDecimal(loanPayment.getAppliedAmount()).negate(), true, true, true);
			nextDueCalculator.updateNextDueDate(theLoan);
			statementLogic.updateBillingStatementsForLoan(loanPayment.getLoanID(), loanPayment.getPayment().getEffectiveDate());
			if(theLoan.getLastPaidDate() == null || theLoan.getLastPaidDate().before(payment.getEffectiveDate()))
				theLoan.setLastPaidDate(payment.getEffectiveDate());
		}
	}
}
