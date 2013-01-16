package org.gsoft.openserv.web.converters.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanState;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.gsoft.openserv.domain.payment.BillingStatement;
import org.gsoft.openserv.repositories.payment.BillingStatementRepository;
import org.gsoft.openserv.service.AccountSummaryService;
import org.gsoft.openserv.web.models.BillingStatementModel;
import org.gsoft.openserv.web.models.LoanAmortizationModel;
import org.gsoft.openserv.web.models.LoanDetailModel;
import org.gsoft.openserv.web.models.LoanFinancialDataModel;
import org.gsoft.openserv.web.models.LoanStateModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanToLoanDetailModelConverter implements Converter<Loan, LoanDetailModel>{
	@Resource
	private AccountSummaryService accountSummaryService;
	@Resource
	private BillingStatementRepository billingStatementRepository;
	@Resource
	private ConversionService conversionService;
	
	public LoanDetailModel convert(Loan loan){
		LoanDetailModel model = new LoanDetailModel();
		LoanFinancialDataModel finModel = new LoanFinancialDataModel();
		finModel.setLoanID(loan.getLoanID());
		finModel.setLoanType(loan.getLoanType());
		LoanStateHistory loanStateHistory = accountSummaryService.getLoanStateHistoryForLoan(loan.getLoanID());
		finModel.setCurrentPrincipal(loanStateHistory.getEndingPrincipal());
		finModel.setCurrentInterest(loanStateHistory.getEndingInterest());
		finModel.setCurrentFees(loanStateHistory.getEndingFees());
		finModel.setBaseRate(loanStateHistory.getEndingBaseRate());
		finModel.setMargin(loanStateHistory.getEndingMargin());
		finModel.setEffectiveIntRate(finModel.getBaseRate().add(finModel.getMargin()));
		finModel.setDailyInterestAmount(loanStateHistory.getEndingInterestRate());
		finModel.setMinimumPaymentAmount(loan.getMinimumPaymentAmount());
		finModel.setNextDueDate(loan.getNextDueDate());
		finModel.setLastPaidDate(loan.getLastPaidDate());
		finModel.setRepaymentStartDate(loan.getRepaymentStartDate());
		finModel.setFirstDueDate(loan.getFirstDueDate());
		finModel.setInitialDueDate(loan.getInitialDueDate());
		finModel.setUsedTerm(loan.getUsedLoanTerm());
		finModel.setRemainingTerm(loan.getRemainingLoanTerm());
		finModel.setCurrentUnpaidDueDate(loan.getCurrentUnpaidDueDate());
		model.setCurrentAmortization(conversionService.convert(accountSummaryService.getAmortizationScheduleForLoan(loan.getLoanID()), LoanAmortizationModel.class));
		ArrayList<LoanStateModel> loanHistory = new ArrayList<LoanStateModel>();
		for(LoanState loanEvent:loanStateHistory.getLoanStates()){
			loanHistory.add(conversionService.convert(loanEvent, LoanStateModel.class));
		}
		List<BillingStatement> billingStatments = billingStatementRepository.findAllBillsForLoan(loan.getLoanID());
		for(BillingStatement statement:billingStatments){
			model.getBillingStatements().add(conversionService.convert(statement, BillingStatementModel.class));
		}
		model.setLoanFinancialData(finModel);
		model.setLoanHistory(loanHistory);
		return model;
	}
}
