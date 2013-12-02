package org.gsoft.openserv.web.accountsummary.converter;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.loanstate.LoanStateHistory;
import org.gsoft.openserv.service.AccountSummaryService;
import org.gsoft.openserv.web.accountsummary.model.LoanSummaryModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanToLoanSummaryModelConverter implements Converter<Loan, LoanSummaryModel>{
	@Resource
	private AccountSummaryService accountSummaryService;
	@Resource
	private SystemSettingsLogic systemSettingsLogic;
	
	public LoanSummaryModel convert(Loan loan){
		Date systemDate = systemSettingsLogic.getCurrentSystemDate();
		LoanSummaryModel model = new LoanSummaryModel();
		model.setLoanID(loan.getLoanID());
		model.setAccountNumber(loan.getAccount().getAccountNumber());
		model.setLoanProgram(loan.getLoanProgram());
		LoanStateHistory loanStateHistory = accountSummaryService.getLoanStateHistoryForLoan(loan.getLoanID());
		model.setCurrentPrincipal(loanStateHistory.getEndingPrincipal());
		model.setCurrentInterest(loanStateHistory.getLoanStateAsOf(systemDate).getInterestThrough(systemDate));
		model.setCurrentFees(loanStateHistory.getEndingFees());
		return model;
	}
}
