package org.gsoft.phoenix.buslogic.loan;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.loanevent.LoanEventLogic;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.LoanRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanLookupLogic {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private LoanEventLogic loanEventLogic;

	public Integer getLoanPrincipalBalanceAsOf(Loan loan, Date asOfDate){
		return loanEventLogic.findMostRecentLoanEventWithTransactionEffectivePriorToDate(loan.getLoanID(), asOfDate).getLoanTransaction().getEndingPrincipal();
	}
	
	public List<Loan> getAllLoansForBorrower(Long borrowerID){
		return loanRepository.findAllLoansByBorrowerPersonID(borrowerID);
	}

	public Loan getLoanByID(Long loanID){
		return loanRepository.findOne(loanID);
	}
}
