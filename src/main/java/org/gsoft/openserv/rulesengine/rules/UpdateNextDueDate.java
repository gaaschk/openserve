package org.gsoft.openserv.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.repayment.NextDueDateCalculator;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.rulesengine.AbstractRule;
import org.gsoft.openserv.rulesengine.FactExpression;
import org.gsoft.openserv.rulesengine.RulesEngine;
import org.gsoft.openserv.rulesengine.facts.EffectiveLoanTypeProfileMissing;
import org.gsoft.openserv.rulesengine.facts.InitialDueDateMissing;
import org.gsoft.openserv.rulesengine.facts.NextDueDateMissing;
import org.springframework.stereotype.Component;

@Component
public class UpdateNextDueDate extends AbstractRule{
	@Resource
	private NextDueDateCalculator nextDueDateCalculator;
	@Resource
	private RulesEngine theRulesEngine;
	@Resource
	private LoanRepository loanRepository;
	
	public UpdateNextDueDate(){
		this.expression.expect(NextDueDateMissing.class, true).and(EffectiveLoanTypeProfileMissing.class, false).and(InitialDueDateMissing.class, false);
	}
	
	@Override
	public void execute(FactExpression factExpression) {
		Loan theOldLoan = factExpression.getFactForClass(NextDueDateMissing.class).getLoan();
		nextDueDateCalculator.updateNextDueDate(theOldLoan);
		Loan theNewLoan = loanRepository.findOne(theOldLoan.getID());
		theRulesEngine.addOrReplaceContext(theOldLoan, theNewLoan);
	}
	
}
