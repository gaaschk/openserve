package org.gsoft.openserv.buslogic.loan;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.loanevent.LoanEventLogic;
import org.gsoft.openserv.domain.loan.Disbursement;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanEvent;
import org.gsoft.openserv.domain.loan.LoanEventType;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.springframework.stereotype.Component;

@Component
public class AddLoanLogic {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private LoanEventLogic loanEventLogic;
	
	public Loan addNewLoan(Loan theLoan){
		Loan savedLoan = this.loanRepository.save(theLoan);
		loanEventLogic.createLoanEvent(savedLoan, LoanEventType.LOAN_ADDED, savedLoan.getServicingStartDate(), 0, BigDecimal.ZERO, 0);
		LoanEvent lastDisbEvent = null;
		for(Disbursement disbursement:savedLoan.getDisbursements()){
			Date disbEffectiveDate = disbursement.getDisbursementEffectiveDate().after(savedLoan.getServicingStartDate())?disbursement.getDisbursementEffectiveDate():savedLoan.getServicingStartDate();
			LoanEvent disbEvent = loanEventLogic.createLoanEvent(savedLoan, LoanEventType.DISBURSEMENT_ADDED, disbEffectiveDate, disbursement.getDisbursementAmount(), BigDecimal.ZERO, 0);
			if(!disbEvent.getEffectiveDate().after(savedLoan.getServicingStartDate())){
				lastDisbEvent = disbEvent;
			}
		}
		if(!savedLoan.getStartingPrincipal().equals(lastDisbEvent.getLoanTransaction().getEndingPrincipal()) ||
				savedLoan.getStartingInterest().compareTo(lastDisbEvent.getLoanTransaction().getEndingInterest()) != 0 ||
				!savedLoan.getStartingFees().equals(lastDisbEvent.getLoanTransaction().getEndingFees())){
			loanEventLogic.createLoanEvent(savedLoan, LoanEventType.LOAN_ADD_ADJUSTMENT, savedLoan.getServicingStartDate(),
					savedLoan.getStartingPrincipal() - lastDisbEvent.getLoanTransaction().getEndingPrincipal(),
					savedLoan.getStartingInterest().subtract(lastDisbEvent.getLoanTransaction().getEndingInterest()),
					savedLoan.getStartingFees() - lastDisbEvent.getLoanTransaction().getEndingFees());
		}
		return savedLoan;
	}
}
