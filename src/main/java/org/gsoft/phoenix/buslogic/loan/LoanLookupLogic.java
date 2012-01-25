package org.gsoft.phoenix.buslogic.loan;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanEventRepository;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanLookupLogic {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private LoanEventRepository loanEventRepository;

	public Integer getLoanPrincipalBalanceAsOf(Loan loan, Date asOfDate){
		return loanEventRepository.findMostRecentLoanEventWithTransactionEffectiveOnOrBeforeDate(loan.getLoanID(), asOfDate).getLoanTransaction().getEndingPrincipal();
	}
	
	public List<Loan> getAllLoansForBorrower(Long borrowerID){
		return loanRepository.findAllLoansByBorrowerPersonID(borrowerID);
	}

	public Loan getLoanByID(Long loanID){
		return loanRepository.findOne(loanID);
	}
}
