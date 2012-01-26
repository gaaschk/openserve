package org.gsoft.phoenix.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.repayment.RepaymentStartDateCalculator;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.gsoft.phoenix.rulesengine.AbstractRule;
import org.gsoft.phoenix.rulesengine.FactExpression;
import org.gsoft.phoenix.rulesengine.RulesEngine;
import org.gsoft.phoenix.rulesengine.facts.EffectiveLoanTypeProfileMissing;
import org.gsoft.phoenix.rulesengine.facts.RepaymentStartDateMissing;
import org.springframework.stereotype.Component;

@Component
public class UpdateRepaymentStartDate extends AbstractRule{
	@Resource
	private RepaymentStartDateCalculator repaymentStartCalculator;
	@Resource
	private RulesEngine theRulesEngine;
	@Resource
	private LoanRepository loanRepository;
	
	public UpdateRepaymentStartDate(){
		this.expression.expect(RepaymentStartDateMissing.class, true).and(EffectiveLoanTypeProfileMissing.class, false);
	}
	
	@Override
	public void execute(FactExpression factExpression) {
		Loan theOldLoan = factExpression.getFactForClass(RepaymentStartDateMissing.class).getLoan();
		repaymentStartCalculator.updateRepaymentStartDate(theOldLoan);
		Loan theNewLoan = loanRepository.findOne(theOldLoan.getID());
		theRulesEngine.addOrReplaceContext(theOldLoan, theNewLoan);
	}
	
}
