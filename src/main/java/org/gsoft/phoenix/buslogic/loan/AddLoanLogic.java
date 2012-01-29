package org.gsoft.phoenix.buslogic.loan;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.loanevent.LoanEventLogic;
import org.gsoft.phoenix.domain.loan.Disbursement;
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
	
	public Loan addNewLoan(Loan loan){
		loan = this.loanRepository.save(loan);
		loanEventLogic.createLoanEvent(loan, LoanEventType.LOAN_ADDED, loan.getServicingStartDate(), 0, BigDecimal.ZERO, 0);
		LoanEvent lastDisbEvent = null;
		for(Disbursement disbursement:loan.getDisbursements()){
			Date disbEffectiveDate = disbursement.getDisbursementEffectiveDate().after(loan.getServicingStartDate())?disbursement.getDisbursementEffectiveDate():loan.getServicingStartDate();
			LoanEvent disbEvent = loanEventLogic.createLoanEvent(loan, LoanEventType.DISBURSEMENT_ADDED, disbEffectiveDate, disbursement.getDisbursementAmount(), BigDecimal.ZERO, 0);
			if(!disbEvent.getEffectiveDate().after(loan.getServicingStartDate())){
				lastDisbEvent = disbEvent;
			}
		}
		if(!loan.getStartingPrincipal().equals(lastDisbEvent.getLoanTransaction().getEndingPrincipal()) ||
				loan.getStartingInterest().compareTo(lastDisbEvent.getLoanTransaction().getEndingInterest()) != 0 ||
				!loan.getStartingFees().equals(lastDisbEvent.getLoanTransaction().getEndingFees())){
			loanEventLogic.createLoanEvent(loan, LoanEventType.LOAN_ADD_ADJUSTMENT, loan.getServicingStartDate(),
					loan.getStartingPrincipal() - lastDisbEvent.getLoanTransaction().getEndingPrincipal(),
					loan.getStartingInterest().subtract(lastDisbEvent.getLoanTransaction().getEndingInterest()),
					loan.getStartingFees() - lastDisbEvent.getLoanTransaction().getEndingFees());
		}
		return loan;
	}
}
