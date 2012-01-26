package org.gsoft.phoenix.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.amortization.RemainingTermCalculator;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.gsoft.phoenix.rulesengine.AbstractRule;
import org.gsoft.phoenix.rulesengine.FactExpression;
import org.gsoft.phoenix.rulesengine.RulesEngine;
import org.gsoft.phoenix.rulesengine.facts.EffectiveLoanTypeProfileMissing;
import org.gsoft.phoenix.rulesengine.facts.RemainingLoanTermMissing;
import org.springframework.stereotype.Component;

@Component
public class SetRemainingLoanTerm extends AbstractRule{
	@Resource
	private RemainingTermCalculator remainingTermCalculator;
	@Resource
	private RulesEngine theRulesEngine;
	@Resource
	private LoanRepository loanRepository;
	
	public SetRemainingLoanTerm(){
		this.expression.expect(RemainingLoanTermMissing.class, true).and(EffectiveLoanTypeProfileMissing.class, false);
	}
	
	@Override
	public void execute(FactExpression factExpression) {
		Loan theOldLoan = factExpression.getFactForClass(RemainingLoanTermMissing.class).getLoan();
		remainingTermCalculator.updateRemainingLoanTerm(theOldLoan);
		Loan theNewLoan = loanRepository.findOne(theOldLoan.getID());
		theRulesEngine.addOrReplaceContext(theOldLoan, theNewLoan);
	}
	
}
