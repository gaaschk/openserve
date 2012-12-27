package org.gsoft.openserv.buslogic.repayment;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.springframework.stereotype.Component;

@Component
public class NextDueDateCalculator {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	
	public void updateNextDueDate(Loan loan){
		loan.setCurrentUnpaidDueDate(loan.getInitialDueDate());
	}
}
