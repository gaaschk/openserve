package org.gsoft.openserv.web.converters.model;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.gsoft.openserv.service.AccountSummaryService;
import org.gsoft.openserv.web.models.LoanSummaryModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanToLoanSummaryModelConverter implements Converter<Loan, LoanSummaryModel>{
	@Resource
	private AccountSummaryService accountSummaryService;
	
	public LoanSummaryModel convert(Loan loan){
		LoanSummaryModel model = new LoanSummaryModel();
		model.setLoanID(loan.getLoanID());
		model.setLoanType(loan.getLoanType());
		LoanStateHistory loanStateHistory = accountSummaryService.getLoanStateHistoryForLoan(loan.getLoanID());
		model.setCurrentPrincipal(loanStateHistory.getEndingPrincipal());
		model.setCurrentInterest(loanStateHistory.getEndingInterest());
		model.setCurrentFees(loanStateHistory.getEndingFees());
		return model;
	}
}
