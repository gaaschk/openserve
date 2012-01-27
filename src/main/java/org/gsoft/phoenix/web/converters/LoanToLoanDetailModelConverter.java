package org.gsoft.phoenix.web.converters;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.service.AccountSummaryService;
import org.gsoft.phoenix.web.models.LoanDetailModel;
import org.gsoft.phoenix.web.models.LoanEventModel;
import org.gsoft.phoenix.web.models.LoanFinancialDataModel;
import org.joda.time.DateTime;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanToLoanDetailModelConverter implements Converter<Loan, LoanDetailModel>{
	@Resource
	private AccountSummaryService accountSummaryService;
	@Resource
	private ConversionService conversionService;
	
	public LoanDetailModel convert(Loan loan){
		LoanDetailModel model = new LoanDetailModel();
		LoanFinancialDataModel finModel = new LoanFinancialDataModel();
		finModel.setLoanID(loan.getLoanID());
		finModel.setLoanType(loan.getLoanType());
		finModel.setCurrentPrincipal(loan.getCurrentPrincipal());
		finModel.setCurrentInterest(loan.getCurrentInterest());
		finModel.setCurrentFees(loan.getCurrentFees());
		finModel.setMinimumPaymentAmount(loan.getMinimumPaymentAmount());
		finModel.setNextDueDate(new DateTime(loan.getInitialDueDate()).plusMonths(loan.getUsedLoanTerm()).toDate());
		finModel.setLastPaidDate(loan.getLastPaidDate());
		finModel.setRepaymentStartDate(loan.getRepaymentStartDate());
		finModel.setFirstDueDate(loan.getFirstDueDate());
		finModel.setInitialDueDate(loan.getInitialDueDate());
		List<LoanEvent> loanEvents = accountSummaryService.getAllLoanEventsForLoan(loan.getLoanID());
		ArrayList<LoanEventModel> loanHistory = new ArrayList<LoanEventModel>();
		for(LoanEvent loanEvent:loanEvents){
			loanHistory.add(conversionService.convert(loanEvent, LoanEventModel.class));
		}
		model.setLoanFinancialData(finModel);
		model.setLoanHistory(loanHistory);
		return model;
	}
}
