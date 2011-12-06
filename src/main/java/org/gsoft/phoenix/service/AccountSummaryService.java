package org.gsoft.phoenix.service;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.MaintainLoanLogic;
import org.gsoft.phoenix.domain.Loan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class AccountSummaryService {
	@Resource
	private MaintainLoanLogic maintainLoanLogic;
	
	public List<Loan> getAllLoansForBorrower(Long borrowerID){
		return maintainLoanLogic.getAllLoansForBorrower(borrowerID);
	}
	
	public Loan getLoanByID(Long loanID){
		return maintainLoanLogic.getLoanByID(loanID);
	}
}
