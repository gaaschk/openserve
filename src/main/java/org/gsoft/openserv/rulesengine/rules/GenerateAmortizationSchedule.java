package org.gsoft.openserv.rulesengine.rules;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.amortization.AmortizationLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.rulesengine.AbstractRule;
import org.gsoft.openserv.rulesengine.FactExpression;
import org.gsoft.openserv.rulesengine.RulesEngine;
import org.gsoft.openserv.rulesengine.facts.EffectiveLoanTypeProfileMissing;
import org.gsoft.openserv.rulesengine.facts.MinimumPaymentAmountMissing;
import org.gsoft.openserv.rulesengine.facts.RemainingLoanTermMissing;
import org.gsoft.openserv.rulesengine.facts.RepaymentStartDateMissing;
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
		this.expression.expect(MinimumPaymentAmountMissing.class, true).and(RemainingLoanTermMissing.class, false).and(EffectiveLoanTypeProfileMissing.class, false).and(RepaymentStartDateMissing.class, false);
	}
	
	@Override
	public void execute(FactExpression factExpression) {
		Loan theOldLoan = factExpression.getFactForClass(MinimumPaymentAmountMissing.class).getLoan();
		ArrayList<Long> theLoanInAList = new ArrayList<Long>();
		theLoanInAList.add(theOldLoan.getLoanID());
		amortizationLogic.createAmortizationSchedule(theLoanInAList, theOldLoan.getRepaymentStartDate());
		Loan theNewLoan = loanRepository.findOne(theOldLoan.getID());
		theRulesEngine.addOrReplaceContext(theOldLoan, theNewLoan);
	}
	
}
