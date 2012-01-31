package org.gsoft.phoenix.rulesengine.rules;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.payment.BillingStatementLogic;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.gsoft.phoenix.rulesengine.AbstractRule;
import org.gsoft.phoenix.rulesengine.FactExpression;
import org.gsoft.phoenix.rulesengine.RulesEngine;
import org.gsoft.phoenix.rulesengine.facts.BillingStatementMissing;
import org.gsoft.phoenix.rulesengine.facts.NextDueDateMissing;
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
