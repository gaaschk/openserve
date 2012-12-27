package org.gsoft.openserv.service.loanentry;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.interest.LoanInterestRateFactory;
import org.gsoft.openserv.buslogic.loan.LoanTypeLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.rulesengine.annotation.RunRulesEngine;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class LoanEntryService {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private LoanInterestRateFactory interestFactory;
	@Resource
	private LoanTypeLogic loanTypeLogic;

	
	@PreAuthorize("hasRole('PERM_AddLoan')")
	@Transactional
	@RunRulesEngine
	public Loan addNewLoan(Loan newLoan){
		loanTypeLogic.updateLoanTypeProfileForLoan(newLoan);
		newLoan = loanRepository.save(newLoan);
		return newLoan;
	}
}
