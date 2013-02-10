package org.gsoft.openserv.web.converters.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanState;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.billing.LoanStatementSummary;
import org.gsoft.openserv.domain.payment.billing.StatementPaySummary;
import org.gsoft.openserv.repositories.payment.LoanPaymentRepository;
import org.gsoft.openserv.repositories.payment.LoanStatementRepository;
import org.gsoft.openserv.service.AccountSummaryService;
import org.gsoft.openserv.util.Constants;
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
	private ConversionService conversionService;
	@Resource
	private SystemSettingsLogic systemSettings;
	@Resource
	private LoanPaymentRepository loanPaymentRepository;
	@Resource
	private LoanStatementRepository statementRepository;
	
	public LoanDetailModel convert(Loan loan){
		Date systemDate = systemSettings.getCurrentSystemDate();
		LoanDetailModel model = new LoanDetailModel();
		LoanFinancialDataModel finModel = new LoanFinancialDataModel();
		finModel.setLoanID(loan.getLoanID());
		finModel.setLoanType(loan.getLoanType());
		LoanStateHistory loanStateHistory = accountSummaryService.getLoanStateHistoryForLoan(loan.getLoanID());
		finModel.setCurrentPrincipal(loanStateHistory.getEndingPrincipal());
		finModel.setCurrentInterest(loanStateHistory.getLoanStateAsOf(systemDate).getInterestThrough(systemDate));
		finModel.setCurrentFees(loanStateHistory.getEndingFees());
		finModel.setBaseRate(loanStateHistory.getEndingBaseRate());
		finModel.setMargin(loanStateHistory.getEndingMargin());
		finModel.setEffectiveIntRate(finModel.getBaseRate().add(finModel.getMargin()));
		finModel.setDailyInterestAmount(loanStateHistory.getEndingInterestRate().multiply(BigDecimal.valueOf(loanStateHistory.getEndingPrincipal())).divide(BigDecimal.valueOf(Constants.DAYS_IN_YEAR), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE));
		finModel.setMinimumPaymentAmount(loan.getMinimumPaymentAmountAsOf(systemSettings.getCurrentSystemDate()));
		LoanStatementSummary statementSummary = statementRepository.getLoanStatementSummaryForLoan(loan);
		finModel.setNextDueDate(statementSummary.getNextDueDate());
		LoanPayment lastPayment = loanPaymentRepository.findMostRecentLoanPayment(loan.getLoanID());
		if(lastPayment != null){
			finModel.setLastPaidDate(lastPayment.getPayment().getEffectiveDate());
		}
		finModel.setRepaymentStartDate(loan.getRepaymentStartDate());
		finModel.setFirstDueDate(loan.getFirstDueDate());
		finModel.setInitialDueDate(loan.getInitialDueDate());
		int remainingTerm = loan.getRemainingLoanTermAsOf(systemDate);
		finModel.setUsedTerm(loan.getEffectiveLoanTypeProfile().getMaximumLoanTerm()-remainingTerm);
		finModel.setRemainingTerm(remainingTerm);
		finModel.setCurrentUnpaidDueDate(statementSummary.getEarliestUnpaidDueDate());
		model.setCurrentAmortization(conversionService.convert(accountSummaryService.getAmortizationScheduleForLoan(loan.getLoanID()), LoanAmortizationModel.class));
		ArrayList<LoanStateModel> loanHistory = new ArrayList<LoanStateModel>();
		ArrayList<LoanState> tempLoanStates = new ArrayList<>();
		tempLoanStates.addAll(loanStateHistory.getLoanStates());
		Collections.reverse(tempLoanStates);
		for(LoanState loanEvent:tempLoanStates){
			loanHistory.add(conversionService.convert(loanEvent, LoanStateModel.class));
		}
		for(StatementPaySummary statement:statementSummary.getStatements()){
			model.getBillingStatements().add(conversionService.convert(statement, BillingStatementModel.class));
		}
		model.setLoanFinancialData(finModel);
		model.setLoanHistory(loanHistory);
		return model;
	}
}
