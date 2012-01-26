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
	
	public Loan addNewLoan(Loan loan, Date effectiveDate){
		loan = this.loanRepository.save(loan);
		loanEventLogic.createLoanEvent(loan, LoanEventType.LOAN_ADDED, effectiveDate, 0, BigDecimal.ZERO, 0);
		LoanEvent lastDisbEvent = null;
		for(Disbursement disbursement:loan.getDisbursements()){
			Date disbEffectiveDate = disbursement.getDisbursementEffectiveDate().after(effectiveDate)?disbursement.getDisbursementEffectiveDate():effectiveDate;
			LoanEvent disbEvent = loanEventLogic.createLoanEvent(loan, LoanEventType.DISBURSEMENT_ADDED, disbEffectiveDate, disbursement.getDisbursementAmount(), BigDecimal.ZERO, 0);
			if(!disbEvent.getEffectiveDate().after(effectiveDate)){
				lastDisbEvent = disbEvent;
			}
		}
		if(!loan.getStartingPrincipal().equals(lastDisbEvent.getLoanTransaction().getEndingPrincipal()) ||
				loan.getStartingInterest().compareTo(lastDisbEvent.getLoanTransaction().getEndingInterest()) != 0 ||
				!loan.getStartingFees().equals(lastDisbEvent.getLoanTransaction().getEndingFees())){
			loanEventLogic.createLoanEvent(loan, LoanEventType.LOAN_ADD_ADJUSTMENT, effectiveDate,
					loan.getStartingPrincipal() - lastDisbEvent.getLoanTransaction().getEndingPrincipal(),
					loan.getStartingInterest().subtract(lastDisbEvent.getLoanTransaction().getEndingInterest()),
					loan.getStartingFees() - lastDisbEvent.getLoanTransaction().getEndingFees());
		}
		return loan;
	}
}
