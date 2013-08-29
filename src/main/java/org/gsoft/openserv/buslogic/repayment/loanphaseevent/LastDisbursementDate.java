package org.gsoft.openserv.buslogic.repayment.loanphaseevent;

import java.util.Date;

import org.gsoft.openserv.domain.loan.Disbursement;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanPhaseEvent;
import org.springframework.stereotype.Component;

@Component
public class LastDisbursementDate implements LoanPhaseEventDateCalculator{

	@Override
	public Date calculateEventDate(Loan loan){
		Date disbDate = null;
		for(Disbursement disb:loan.getDisbursements()){
			if(disbDate == null || disbDate.before(disb.getDisbursementEffectiveDate())){
				disbDate = disb.getDisbursementEffectiveDate();
			}
		}
		return disbDate;
	}

	@Override
	public LoanPhaseEvent calculatorFor() {
		return LoanPhaseEvent.LAST_DISB_DATE;
	}

}
