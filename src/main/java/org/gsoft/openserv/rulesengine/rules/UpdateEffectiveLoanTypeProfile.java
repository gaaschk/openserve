package org.gsoft.openserv.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.loan.LoanTypeLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.rulesengine.AbstractRule;
import org.gsoft.openserv.rulesengine.FactExpression;
import org.gsoft.openserv.rulesengine.RulesEngine;
import org.gsoft.openserv.rulesengine.facts.EffectiveLoanTypeProfileMissing;
import org.springframework.stereotype.Component;

@Component
public class UpdateEffectiveLoanTypeProfile extends AbstractRule{
	@Resource
	private LoanTypeLogic loanTypeLogic;
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
		loanTypeLogic.updateLoanTypeProfileForLoan(theOldLoan);
		Loan theNewLoan = loanRepository.findOne(theOldLoan.getID());
		theRulesEngine.addOrReplaceContext(theOldLoan, theNewLoan);
	}
	
}
