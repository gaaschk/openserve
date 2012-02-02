package org.gsoft.openserv.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.payment.BillingStatementLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.rulesengine.AbstractRule;
import org.gsoft.openserv.rulesengine.FactExpression;
import org.gsoft.openserv.rulesengine.RulesEngine;
import org.gsoft.openserv.rulesengine.facts.BillingStatementMissing;
import org.gsoft.openserv.rulesengine.facts.NextDueDateMissing;
import org.springframework.stereotype.Component;

@Component
public class GenerateBillingStatement extends AbstractRule{
	@Resource
	private BillingStatementLogic billingStatementLogic;
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private RulesEngine rulesEngine;
	

	public GenerateBillingStatement() {
		this.expression.expect(BillingStatementMissing.class, true).and(NextDueDateMissing.class, false);
	}

	@Override
	public void execute(FactExpression factExpression) {
		Loan theLoan = factExpression.getFactForClass(BillingStatementMissing.class).getLoan();
		Loan theNewLoan = loanRepository.findOne(theLoan.getLoanID());
		billingStatementLogic.createBillingStatement(theNewLoan);
		rulesEngine.replaceContext(theLoan, theNewLoan);
	}
	
}
