package org.gsoft.phoenix.service.loanentry;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.loan.AddLoanLogic;
import org.gsoft.phoenix.buslogic.system.SystemSettingsLogic;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.rulesengine.RunRulesEngine;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class LoanEntryService {
	@Resource
	private AddLoanLogic maintainLoanLogic;
	@Resource
	private SystemSettingsLogic systemSettingsLogic;
	
	@PreAuthorize("hasRole('PERM_AddLoan')")
	@Transactional
	@RunRulesEngine
	public Loan addNewLoan(Loan newLoan){
		newLoan.setMargin(new BigDecimal(0));
		newLoan = maintainLoanLogic.addNewLoan(newLoan, systemSettingsLogic.getCurrentSystemDate());
		return newLoan;
	}
}
