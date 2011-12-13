package org.gsoft.phoenix.web.controller.accountsummary.model;

import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.springframework.stereotype.Component;

@Component
public class LoanEventModelConverter {
	public LoanEventModel convertToModel(LoanEvent loanEvent){
		LoanEventModel loanEventModel = new LoanEventModel();
		loanEventModel.setLoanID(loanEvent.getLoanID());
		loanEventModel.setLoanEventType(loanEvent.getLoanEventType().getLoanEventName());
		loanEventModel.setLoanEventEffectiveDate(loanEvent.getEffectiveDate());
		loanEventModel.setLoanEventPostDate(loanEvent.getPostDate());
		if(loanEvent.getLoanTransaction() != null){
			loanEventModel.setInterestAccrued(loanEvent.getLoanTransaction().getInterestAccrued());
			loanEventModel.setInterestPaid(loanEvent.getLoanTransaction().getInterestPaid());
			loanEventModel.setPrincipalChange(loanEvent.getLoanTransaction().getPrincipalChange());
			loanEventModel.setInterestChange(loanEvent.getLoanTransaction().getInterestChange());
			loanEventModel.setFeesChange(loanEvent.getLoanTransaction().getFeesChange());
			loanEventModel.setEndingPrincipal(loanEvent.getLoanTransaction().getEndingPrincipal());
			loanEventModel.setEndingInterest(loanEvent.getLoanTransaction().getEndingInterest());
			loanEventModel.setEndingFees(loanEvent.getLoanTransaction().getEndingFees());
		}
		return loanEventModel;
	}
}
