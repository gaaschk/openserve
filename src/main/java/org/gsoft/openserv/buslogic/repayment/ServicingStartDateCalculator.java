package org.gsoft.openserv.buslogic.repayment;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanEvent;
import org.gsoft.openserv.domain.loan.LoanEventType;
import org.gsoft.openserv.repositories.loan.LoanEventRepository;
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
