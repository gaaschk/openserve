package org.gsoft.openserv.rulesengine.dataaccess;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.payment.BillingStatement;
import org.gsoft.openserv.repositories.payment.BillingStatementRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanRuleDataLoader implements RuleDataLoader<Loan> {
	@Resource
	private BillingStatementRepository billingStatementRepository;
	
	@Override
	public Class<Loan> forType() {
		return Loan.class;
	}

	@Override
	public List<Object> loadRuleRelatedData(Loan contextObject) {
		ArrayList<Object> objects = new ArrayList<Object>();
		objects.add(this.loadMostRecentBillingStatement(contextObject));
		objects.add(contextObject.getEffectiveLoanTypeProfile());
		objects.addAll(contextObject.getDisbursements());
		return objects;
	}
	
	private BillingStatement loadMostRecentBillingStatement(Loan contextObject){
		BillingStatement lastStatement = billingStatementRepository.findMostRecentBillingStatementForLoan(contextObject.getLoanID());
		return lastStatement;
	}
}
