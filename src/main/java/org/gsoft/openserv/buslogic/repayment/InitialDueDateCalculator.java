package org.gsoft.openserv.buslogic.repayment;

import org.gsoft.openserv.domain.loan.Loan;
import org.springframework.stereotype.Component;

@Component
public class InitialDueDateCalculator {
	
	public void updateInitialDueDate(Loan loan){
		if(loan.getInitialDueDate() == null){
			loan.setInitialDueDate(loan.getFirstDueDate());
		}
	}
}
