package org.gsoft.phoenix.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.repayment.InitialDueDateCalculator;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.gsoft.phoenix.rulesengine.AbstractRule;
import org.gsoft.phoenix.rulesengine.FactExpression;
import org.gsoft.phoenix.rulesengine.RulesEngine;
import org.gsoft.phoenix.rulesengine.facts.EffectiveLoanTypeProfileMissing;
import org.gsoft.phoenix.rulesengine.facts.InitialDueDateMissing;
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
