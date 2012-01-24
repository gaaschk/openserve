package org.gsoft.phoenix.buslogic.loan;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.loanevent.LoanEventLogic;
import org.gsoft.phoenix.domain.loan.Disbursement;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.domain.loan.LoanEventType;
import org.gsoft.phoenix.domain.loan.LoanTypeProfile;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.springframework.stereotype.Component;

@Component
public class AddLoanLogic {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private LoanEventLogic loanEventLogic;
	@Resource
	private LoanTypeLogic loanTypeLogic;
	
	public Loan addNewLoan(Loan loan, Date effectiveDate){
		loan = this.loanRepository.save(loan);
		LoanTypeProfile loanTypeProfile = loanTypeLogic.getLoanTypeProfileForLoan(loan);
		loan.setRemainingLoanTerm(loanTypeProfile.getMaximumLoanTerm());
		LoanEvent addedEvent = loanEventLogic.createLoanEventWithTransaction(loan, LoanEventType.LOAN_ADDED, effectiveDate);
		loanEventLogic.applyLoanEvent(addedEvent);
		LoanEvent lastDisbEvent = null;
		for(Disbursement disbursement:loan.getDisbursements()){
			LoanEvent disbEvent = loanEventLogic.createLoanEventWithTransaction(loan, LoanEventType.DISBURSEMENT_ADDED, effectiveDate);
			disbEvent.getLoanTransaction().setPrincipalChange(disbursement.getDisbursementAmount());
			lastDisbEvent = loanEventLogic.applyLoanEvent(disbEvent);
		}
		LoanEvent adjustmentEvent = loanEventLogic.createLoanEventWithTransaction(loan, LoanEventType.LOAN_ADD_ADJUSTMENT, effectiveDate);
		adjustmentEvent.getLoanTransaction().setPrincipalChange(loan.getStartingPrincipal() - lastDisbEvent.getLoanTransaction().getEndingPrincipal());
		adjustmentEvent.getLoanTransaction().setInterestChange(loan.getStartingInterest().subtract(lastDisbEvent.getLoanTransaction().getEndingInterest()));
		adjustmentEvent.getLoanTransaction().setFeesChange(loan.getStartingFees() - lastDisbEvent.getLoanTransaction().getEndingFees());
		if(adjustmentEvent.getLoanTransaction().getInterestChange().compareTo(BigDecimal.ZERO) != 0 ||
				adjustmentEvent.getLoanTransaction().getPrincipalChange() != 0 ||
				adjustmentEvent.getLoanTransaction().getFeesChange() != 0)
			loanEventLogic.applyLoanEvent(adjustmentEvent);
		return loan;
	}
}
