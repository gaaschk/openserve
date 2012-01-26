package org.gsoft.phoenix.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.repayment.FirstDueDateCalculator;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.gsoft.phoenix.rulesengine.AbstractRule;
import org.gsoft.phoenix.rulesengine.FactExpression;
import org.gsoft.phoenix.rulesengine.RulesEngine;
import org.gsoft.phoenix.rulesengine.facts.EffectiveLoanTypeProfileMissing;
import org.gsoft.phoenix.rulesengine.facts.FirstDueDateMissing;
import org.gsoft.phoenix.rulesengine.facts.RepaymentStartDateMissing;
import org.springframework.stereotype.Component;

@Component
public class UpdateFirstDueDate extends AbstractRule{
	@Resource
	private FirstDueDateCalculator firstDueDateCalculator;
	@Resource
	private RulesEngine theRulesEngine;
	@Resource
	private LoanRepository loanRepository;
	
	public UpdateFirstDueDate(){
		this.expression.expect(FirstDueDateMissing.class, true).and(EffectiveLoanTypeProfileMissing.class, false).and(RepaymentStartDateMissing.class, false);
	}
	
	@Override
	public void execute(FactExpression factExpression) {
		Loan theOldLoan = factExpression.getFactForClass(FirstDueDateMissing.class).getLoan();
		firstDueDateCalculator.updateFirstDueDate(theOldLoan);
		Loan theNewLoan = loanRepository.findOne(theOldLoan.getID());
		theRulesEngine.addOrReplaceContext(theOldLoan, theNewLoan);
	}
	
}
