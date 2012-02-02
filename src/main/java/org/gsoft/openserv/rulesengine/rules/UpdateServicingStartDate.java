package org.gsoft.openserv.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.repayment.ServicingStartDateCalculator;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.rulesengine.AbstractRule;
import org.gsoft.openserv.rulesengine.FactExpression;
import org.gsoft.openserv.rulesengine.RulesEngine;
import org.gsoft.openserv.rulesengine.facts.ServicingStartDateMissing;
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
