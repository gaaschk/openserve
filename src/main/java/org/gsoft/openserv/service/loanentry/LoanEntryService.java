package org.gsoft.openserv.service.loanentry;

import javax.annotation.Resource;

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

	
	@PreAuthorize("hasRole('PERM_AddLoan')")
	@Transactional
	@RunRulesEngine
	public Loan addNewLoan(Loan newLoan){
		newLoan = loanRepository.save(newLoan);
		return newLoan;
	}
}
