package org.gsoft.phoenix.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.repayment.ServicingStartDateCalculator;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.gsoft.phoenix.rulesengine.AbstractRule;
import org.gsoft.phoenix.rulesengine.FactExpression;
import org.gsoft.phoenix.rulesengine.RulesEngine;
import org.gsoft.phoenix.rulesengine.facts.ServicingStartDateMissing;
import org.springframework.stereotype.Component;

@Component
public class UpdateServicingStartDate extends AbstractRule{
	@Resource
	private ServicingStartDateCalculator servicingStartDateCalculator;
	@Resource
	private RulesEngine theRulesEngine;
	@Resource
	private LoanRepository loanRepository;
	
	public UpdateServicingStartDate(){
		this.expression.expect(ServicingStartDateMissing.class, true);
	}
	
	@Override
	public void execute(FactExpression factExpression) {
		Loan theOldLoan = factExpression.getFactForClass(ServicingStartDateMissing.class).getLoan();
		Loan theNewLoan = loanRepository.findOne(theOldLoan.getID());
		servicingStartDateCalculator.updateServicingStartDate(theNewLoan);
		theRulesEngine.addOrReplaceContext(theOldLoan, theNewLoan);
	}
	
}
