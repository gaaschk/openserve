package org.gsoft.openserv.buslogic.repayment.loanphaseevent;

import java.util.Date;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanPhaseEvent;
import org.springframework.stereotype.Component;

@Component
public class GradSepDate implements LoanPhaseEventDateCalculator{

	@Override
	public Date calculateEventDate(Loan loan) {
		return null;
	}

	@Override
	public LoanPhaseEvent calculatorFor() {
		return LoanPhaseEvent.GRAD_SEP_DATE;
	}

}
