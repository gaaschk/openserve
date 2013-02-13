package org.gsoft.openserv.web.converters.model;

import org.gsoft.openserv.domain.loan.LoanState;
import org.gsoft.openserv.web.models.LoanStateModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanStateToLoanStateModelConverter implements Converter<LoanState, LoanStateModel>{
	public LoanStateModel convert(LoanState loanState){
		LoanStateModel loanStateModel = new LoanStateModel();
		loanStateModel.setEventType(loanState.getDescription());
		loanStateModel.setEffectiveDate(loanState.getStateEffectiveDate());
		loanStateModel.setPostedDate(loanState.getStatePostDate());
		loanStateModel.setEndingFees(loanState.getFees());
		loanStateModel.setEndingInterest(loanState.getInterest());
		loanStateModel.setEndingPrincipal(loanState.getPrincipal());
		loanStateModel.setStartingFees(loanState.getFees() - loanState.getFeesChange());
		loanStateModel.setStartingInterest(loanState.getInterest().subtract(loanState.getAccruedInterest().add(loanState.getInterestChange())));
		loanStateModel.setStartingPrincipal(loanState.getPrincipal() - loanState.getPrincipalChange());
		loanStateModel.setAccruedInterest(loanState.getAccruedInterest());
		return loanStateModel;
	}
}
