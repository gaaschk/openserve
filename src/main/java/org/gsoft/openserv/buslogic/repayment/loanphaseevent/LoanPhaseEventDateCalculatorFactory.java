package org.gsoft.openserv.buslogic.repayment.loanphaseevent;

import java.util.HashMap;
import java.util.Map;

import org.gsoft.openserv.domain.loan.LoanPhaseEvent;
import org.springframework.stereotype.Component;

@Component
public class LoanPhaseEventDateCalculatorFactory {
	private Map<LoanPhaseEvent, LoanPhaseEventDateCalculator> loanPhaseEventCalculators;

	public Map<LoanPhaseEvent, LoanPhaseEventDateCalculator> getLoanPhaseEventCalculators() {
		if(this.loanPhaseEventCalculators == null){
			this.loanPhaseEventCalculators = new HashMap<>();
		}
		return loanPhaseEventCalculators;
	}

	public void setLoanPhaseEventCalculators(
			Map<LoanPhaseEvent, LoanPhaseEventDateCalculator> loanPhaseEventCalculators) {
		this.loanPhaseEventCalculators = loanPhaseEventCalculators;
	}

	public void addLoanPhaseEventDateCalculator(LoanPhaseEventDateCalculator calculator){
		this.getLoanPhaseEventCalculators().put(calculator.calculatorFor(), calculator);
	}
}
