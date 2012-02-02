package org.gsoft.openserv.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.repayment.InitialDueDateCalculator;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.rulesengine.AbstractRule;
import org.gsoft.openserv.rulesengine.FactExpression;
import org.gsoft.openserv.rulesengine.RulesEngine;
import org.gsoft.openserv.rulesengine.facts.EffectiveLoanTypeProfileMissing;
import org.gsoft.openserv.rulesengine.facts.InitialDueDateMissing;
import org.springframework.stereotype.Component;

@Component
public class UpdateInitialDueDate extends AbstractRule{
	@Resource
	private InitialDueDateCalculator initialDueDateCalculator;
	@Resource
	private RulesEngine theRulesEngine;
	@Resource
	private LoanRepository loanRepository;
	
	public UpdateInitialDueDate(){
		this.expression.expect(InitialDueDateMissing.class, true).and(EffectiveLoanTypeProfileMissing.class, false);
	}
	
	@Override
	public void execute(FactExpression factExpression) {
		Loan theOldLoan = factExpression.getFactForClass(InitialDueDateMissing.class).getLoan();
		initialDueDateCalculator.updateInitialDueDate(theOldLoan);
		Loan theNewLoan = loanRepository.findOne(theOldLoan.getID());
		theRulesEngine.addOrReplaceContext(theOldLoan, theNewLoan);
	}
	
}
