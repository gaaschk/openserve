package org.gsoft.openserv.buslogic.repayment.loanphaseevent;

import java.util.Date;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanPhaseEvent;

public interface LoanPhaseEventDateCalculator {
	public LoanPhaseEvent calculatorFor();
	public Date calculateEventDate(Loan loan);
}
