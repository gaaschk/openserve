package org.gsoft.phoenix.buslogic.repayment;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.domain.loan.LoanEventType;
import org.gsoft.phoenix.repositories.loan.LoanEventRepository;
import org.springframework.stereotype.Component;

@Component
public class ServicingStartDateCalculator {
	@Resource
	private LoanEventRepository loanEventRepository;
	
	public void updateServicingStartDate(Loan loan){
		LoanEvent loanEvent = loanEventRepository.findAllLoanEventsOfTypeForLoan(loan.getLoanID(), LoanEventType.LOAN_ADDED).get(0);
		loan.setServicingStartDate(loanEvent.getEffectiveDate());
	}
}
