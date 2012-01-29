package org.gsoft.phoenix.buslogic.repayment;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.domain.loan.LoanEventType;
import org.gsoft.phoenix.domain.loan.LoanTypeProfile;
import org.gsoft.phoenix.repositories.loan.LoanEventRepository;
import org.gsoft.phoenix.repositories.loan.LoanTypeProfileRepository;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.stereotype.Component;

@Component
public class NextDueDateCalculator {
	@Resource
	private LoanEventRepository loanEventRepository;
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	
	public void updateNextDueDate(Loan loan){
		loan.setCurrentUnpaidDueDate(loan.getInitialDueDate());
		LoanTypeProfile ltp = loanTypeProfileRepository.findOne(loan.getEffectiveLoanTypeProfileID());
		List<LoanEvent> paymentsForLoan = loanEventRepository.findAllLoanEventsOfTypeForLoan(loan.getLoanID(), LoanEventType.PAYMENT_APPLIED);
		Collections.reverse(paymentsForLoan);
		int accumulatedPaymentAmount = 0;
		for(LoanEvent paymentEvent:paymentsForLoan){
			DateTime paymentDate = new DateTime(paymentEvent.getEffectiveDate());
			DateTime currentDue = new DateTime(loan.getCurrentUnpaidDueDate()).minusDays(ltp.getPrepaymentDays());
			//if payment was made prior to the due date's window, don't advance
			if(paymentDate.isBefore(currentDue))
				continue;
			accumulatedPaymentAmount += paymentEvent.getLoanTransaction().getTransactionNetAmount().negate().intValue();
			if(accumulatedPaymentAmount > loan.getMinimumPaymentAmount()){
				int monthsToAdvance = accumulatedPaymentAmount/loan.getMinimumPaymentAmount();
				int monthsToCurrent = Months.monthsBetween(currentDue,paymentDate).getMonths()+1;
				monthsToAdvance = (monthsToAdvance <= monthsToCurrent)?monthsToAdvance:monthsToCurrent;
				loan.setCurrentUnpaidDueDate(currentDue.plusMonths(monthsToAdvance).plusDays(ltp.getPrepaymentDays()).toDate());
				accumulatedPaymentAmount = monthsToAdvance*loan.getMinimumPaymentAmount();
			}
		}
	}
}
