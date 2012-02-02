package org.gsoft.openserv.service.loanentry;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.interest.LoanInterestRateFactory;
import org.gsoft.openserv.buslogic.loan.AddLoanLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.rulesengine.annotation.RunRulesEngine;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class LoanEntryService {
	@Resource
	private AddLoanLogic maintainLoanLogic;
	@Resource
	private LoanInterestRateFactory interestFactory;

	
	@PreAuthorize("hasRole('PERM_AddLoan')")
	@Transactional
	@RunRulesEngine
	public Loan addNewLoan(Loan newLoan){
		newLoan.setMargin(new BigDecimal(0));
		newLoan.setBaseRate(interestFactory.getBaseRateForLoan(newLoan));
		newLoan = maintainLoanLogic.addNewLoan(newLoan);
		return newLoan;
	}
}
