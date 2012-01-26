package org.gsoft.phoenix.buslogic.repayment;

import org.gsoft.phoenix.domain.loan.Loan;
import org.springframework.stereotype.Component;

@Component
public class InitialDueDateCalculator {
	
	public void updateInitialDueDate(Loan loan){
		loan.setInitialDueDate(loan.getFirstDueDate());
	}
}
