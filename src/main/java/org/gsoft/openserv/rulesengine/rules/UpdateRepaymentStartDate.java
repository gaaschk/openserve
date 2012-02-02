package org.gsoft.openserv.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.repayment.RepaymentStartDateCalculator;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.rulesengine.AbstractRule;
import org.gsoft.openserv.rulesengine.FactExpression;
import org.gsoft.openserv.rulesengine.RulesEngine;
import org.gsoft.openserv.rulesengine.facts.EffectiveLoanTypeProfileMissing;
import org.gsoft.openserv.rulesengine.facts.RepaymentStartDateMissing;
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
