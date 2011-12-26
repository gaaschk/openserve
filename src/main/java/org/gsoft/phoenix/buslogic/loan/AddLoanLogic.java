package org.gsoft.phoenix.buslogic.loan;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.loanevent.LoanEventLogic;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.domain.loan.LoanEventType;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.springframework.stereotype.Component;

@Component
public class AddLoanLogic {
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
}
