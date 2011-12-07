package org.gsoft.phoenix.service;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.MaintainLoanLogic;
import org.gsoft.phoenix.domain.Loan;
import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.repositories.loan.LoanEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class AccountSummaryService {
	@Resource
	private MaintainLoanLogic maintainLoanLogic;
	@Resource
	private LoanEventRepository loanEventRepository;
	
	public List<Loan> getAllLoansForBorrower(Long borrowerID){
		return maintainLoanLogic.getAllLoansForBorrower(borrowerID);
	}
	
	public Loan getLoanByID(Long loanID){
		return maintainLoanLogic.getLoanByID(loanID);
	}
	
	public List<LoanEvent> getAllLoanEventsForLoan(Long loanID){
		return loanEventRepository.findAllByLoanID(loanID);
	}
}
