package org.gsoft.phoenix.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.loan.LoanTypeLogic;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.gsoft.phoenix.rulesengine.AbstractRule;
import org.gsoft.phoenix.rulesengine.FactExpression;
import org.gsoft.phoenix.rulesengine.RulesEngine;
import org.gsoft.phoenix.rulesengine.facts.EffectiveLoanTypeProfileMissing;
import org.springframework.stereotype.Component;

@Component
public class UpdateEffectiveLoanTypeProfile extends AbstractRule{
	@Resource
	private LoanTypeLogic laonTypeLogic;
	@Resource
	private RulesEngine theRulesEngine;
	@Resource
	private LoanRepository loanRepository;
	
	public UpdateEffectiveLoanTypeProfile(){
		this.expression.expect(EffectiveLoanTypeProfileMissing.class, true);
	}
	
	@Override
	public void execute(FactExpression factExpression) {
		Loan theOldLoan = factExpression.getFactForClass(EffectiveLoanTypeProfileMissing.class).getLoan();
		laonTypeLogic.updateLoanTypeProfileForLoan(theOldLoan);
		Loan theNewLoan = loanRepository.findOne(theOldLoan.getID());
		theRulesEngine.addOrReplaceContext(theOldLoan, theNewLoan);
	}
	
}
