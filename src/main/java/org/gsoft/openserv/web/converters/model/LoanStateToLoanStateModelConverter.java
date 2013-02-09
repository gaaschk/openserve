package org.gsoft.openserv.web.converters.model;

import org.gsoft.openserv.domain.loan.BalanceAdjustmentLoanState;
import org.gsoft.openserv.domain.loan.DisbursementLoanState;
import org.gsoft.openserv.domain.loan.LateFeeState;
import org.gsoft.openserv.domain.loan.LoanState;
import org.gsoft.openserv.domain.loan.PaymentLoanState;
import org.gsoft.openserv.domain.loan.RateValueLoanState;
import org.gsoft.openserv.web.models.LoanStateModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanStateToLoanStateModelConverter implements Converter<LoanState, LoanStateModel>{
	public LoanStateModel convert(LoanState loanState){
		LoanStateModel loanStateModel = new LoanStateModel();
		if(loanState instanceof DisbursementLoanState){
			loanStateModel.setEventType("Disbursement");
		}
		else if(loanState instanceof RateValueLoanState){
			loanStateModel.setEventType("Interest Rate Change");
		}
		else if(loanState instanceof BalanceAdjustmentLoanState){
			loanStateModel.setEventType("Administrative Adjustment");
		}
		else if(loanState instanceof PaymentLoanState){
			loanStateModel.setEventType("Payment Applied");
		}
		else if(loanState instanceof LateFeeState){
			loanStateModel.setEventType("Late Fee Assessed");
		}
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
