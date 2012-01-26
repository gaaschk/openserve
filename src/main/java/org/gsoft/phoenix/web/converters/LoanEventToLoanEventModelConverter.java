package org.gsoft.phoenix.web.converters;

import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.web.models.LoanEventModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanEventToLoanEventModelConverter implements Converter<LoanEvent, LoanEventModel>{
	public LoanEventModel convert(LoanEvent loanEvent){
		LoanEventModel loanEventModel = new LoanEventModel();
		loanEventModel.setLoanID(loanEvent.getLoanID());
		loanEventModel.setLoanEventType(loanEvent.getLoanEventType().getLoanEventName());
		loanEventModel.setLoanEventEffectiveDate(loanEvent.getEffectiveDate());
		loanEventModel.setLoanEventPostDate(loanEvent.getPostDate());
		if(loanEvent.getLoanTransaction() != null){
			loanEventModel.setInterestAccrued(loanEvent.getLoanTransaction().getInterestAccrued());
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
