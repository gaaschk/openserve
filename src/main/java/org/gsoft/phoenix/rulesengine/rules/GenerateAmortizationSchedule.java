package org.gsoft.phoenix.rulesengine.rules;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.amortization.AmortizationLogic;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.gsoft.phoenix.rulesengine.AbstractRule;
import org.gsoft.phoenix.rulesengine.FactExpression;
import org.gsoft.phoenix.rulesengine.RulesEngine;
import org.gsoft.phoenix.rulesengine.facts.MinimumPaymentAmountMissing;
import org.springframework.stereotype.Component;

@Component
public class GenerateAmortizationSchedule extends AbstractRule{
	@Resource
	private AmortizationLogic amortizationLogic;
	@Resource
	private RulesEngine theRulesEngine;
	@Resource
	private LoanRepository loanRepository;
	
	public GenerateAmortizationSchedule(){
		this.expression.expect(MinimumPaymentAmountMissing.class, true);
	}
	
	@Override
	public void execute(FactExpression factExpression) {
		Loan theOldLoan = factExpression.getFactForClass(MinimumPaymentAmountMissing.class).getLoan();
		ArrayList<Long> theLoanInAList = new ArrayList<Long>();
		theLoanInAList.add(theOldLoan.getLoanID());
		amortizationLogic.createAmortizationSchedule(theLoanInAList);
		Loan theNewLoan = loanRepository.findOne(theOldLoan.getID());
		theRulesEngine.addOrReplaceContext(theOldLoan, theNewLoan);
//		theOldLoan.setMinimumPaymentAmount(theNewLoan.getMinimumPaymentAmount());
	}
	
}
