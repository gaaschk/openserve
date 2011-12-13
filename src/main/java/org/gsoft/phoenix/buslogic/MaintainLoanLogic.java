package org.gsoft.phoenix.buslogic;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.loanevent.LoanEventLogic;
import org.gsoft.phoenix.domain.Loan;
import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.domain.loan.LoanEventType;
import org.gsoft.phoenix.repositories.LoanRepository;
import org.springframework.stereotype.Component;

@Component
public class MaintainLoanLogic {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private LoanEventLogic loanEventLogic;
	
	public Loan addNewLoan(Loan loan, Date effectiveDate){
		Loan savedLoan = this.loanRepository.save(loan);
		LoanEvent addedEvent = loanEventLogic.createLoanEventWithTransaction(savedLoan, LoanEventType.LOAN_ADDED, 
				effectiveDate);
		addedEvent.getLoanTransaction().setFeesChange(loan.getStartingFees());
		addedEvent.getLoanTransaction().setInterestChange(loan.getStartingInterest());
		addedEvent.getLoanTransaction().setPrincipalChange(loan.getStartingPrincipal());
		loanEventLogic.applyLoanEvent(addedEvent);
		return savedLoan;
	}
	
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
