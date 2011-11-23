package org.gsoft.phoenix.service;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.MaintainLoanLogic;
import org.gsoft.phoenix.buslogic.MaintainPersonLogic;
import org.gsoft.phoenix.buslogic.system.SystemSettingsLogic;
import org.gsoft.phoenix.domain.Loan;
import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.service.domain.LoanEntryDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class LoanEntryService {
	@Resource
	private MaintainLoanLogic maintainLoanLogic;
	@Resource
	private MaintainPersonLogic maintainPersonLogic;
	@Resource
	private SystemSettingsLogic systemSettingsLogic;
	
	@Transactional
	public Long addNewLoan(LoanEntryDocument loanEntryDocument){
		Loan newLoan = new Loan();
		newLoan.setStartingPrincipal(loanEntryDocument.getStartingPrincipal());
		newLoan.setStartingInterest(loanEntryDocument.getStartingInterest());
		newLoan.setStartingFees(loanEntryDocument.getStartingFees());
		
		Person borrower = maintainPersonLogic.savePerson(loanEntryDocument.getBorrower());
		newLoan.setBorrowerPersonID(borrower.getPersonID());

		newLoan.setMargin(new BigDecimal(0));
		newLoan = maintainLoanLogic.addNewLoan(newLoan, systemSettingsLogic.getCurrentSystemDate());
		return newLoan.getLoanID();
	}
}
