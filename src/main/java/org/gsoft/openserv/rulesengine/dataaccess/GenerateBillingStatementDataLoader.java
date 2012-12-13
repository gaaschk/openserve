package org.gsoft.openserv.rulesengine.dataaccess;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.payment.BillingStatement;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.gsoft.openserv.repositories.payment.BillingStatementRepository;
import org.springframework.stereotype.Component;

@Component
public class GenerateBillingStatementDataLoader implements RuleDataLoader<Loan> {
	@Resource
	private BillingStatementRepository billingStatementRepository;
	@Resource
	private SystemSettingsLogic systemSettings;
	@Resource
	private LoanTypeProfileRepository ltpRepository;
	
	@Override
	public Class<Loan> forType() {
		return Loan.class;
	}

	@Override
	public ArrayList<Object> loadRuleRelatedData(Loan contextObject) {
		ArrayList<Object> objects = new ArrayList<Object>();
		BillingStatement lastStatement = billingStatementRepository.findMostRecentBillingStatementForLoan(contextObject.getLoanID());
		LoanTypeProfile ltp = ltpRepository.findOne(contextObject.getEffectiveLoanTypeProfileID());
		objects.add(lastStatement);
		objects.add(ltp);
		return objects;
	}

}
