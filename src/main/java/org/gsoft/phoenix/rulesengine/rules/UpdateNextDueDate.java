package org.gsoft.phoenix.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.repayment.NextDueDateCalculator;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.gsoft.phoenix.rulesengine.AbstractRule;
import org.gsoft.phoenix.rulesengine.FactExpression;
import org.gsoft.phoenix.rulesengine.RulesEngine;
import org.gsoft.phoenix.rulesengine.facts.EffectiveLoanTypeProfileMissing;
import org.gsoft.phoenix.rulesengine.facts.InitialDueDateMissing;
import org.gsoft.phoenix.rulesengine.facts.NextDueDateMissing;
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
