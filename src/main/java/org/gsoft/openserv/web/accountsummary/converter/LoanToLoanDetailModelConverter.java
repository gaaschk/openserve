package org.gsoft.openserv.web.accountsummary.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.amortization.AmortizationLogic;
import org.gsoft.openserv.buslogic.amortization.LoanTermCalculator;
import org.gsoft.openserv.buslogic.repayment.RepaymentStartDateCalculator;
import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgramSettings;
import org.gsoft.openserv.domain.loan.LoanState;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.billing.LoanStatementSummary;
import org.gsoft.openserv.domain.payment.billing.StatementPaySummary;
import org.gsoft.openserv.repositories.loan.LoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.payment.LoanPaymentRepository;
import org.gsoft.openserv.repositories.payment.LoanStatementRepository;
import org.gsoft.openserv.service.AccountSummaryService;
import org.gsoft.openserv.util.Constants;
import org.gsoft.openserv.web.accountsummary.model.BillingStatementModel;
import org.gsoft.openserv.web.accountsummary.model.LoanAmortizationModel;
import org.gsoft.openserv.web.accountsummary.model.LoanDetailModel;
import org.gsoft.openserv.web.accountsummary.model.LoanFinancialDataModel;
import org.gsoft.openserv.web.accountsummary.model.LoanStateModel;
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
	@Resource
	private AmortizationLogic amortizationLogic;
	@Resource
	private RepaymentStartDateCalculator repaymentStartCalculator;
	@Resource
	private LoanTermCalculator loanTermCalculator;
	@Resource
	private LoanProgramSettingsRepository loanProgramSettingsRepository;
	
	public LoanDetailModel convert(Loan loan){
		Date systemDate = systemSettings.getCurrentSystemDate();
		LoanDetailModel model = new LoanDetailModel();
		LoanFinancialDataModel finModel = new LoanFinancialDataModel();
		finModel.setLoanID(loan.getLoanID());
		finModel.setLoanProgram(loan.getLoanProgram());
		LoanStateHistory loanStateHistory = accountSummaryService.getLoanStateHistoryForLoan(loan.getLoanID());
		finModel.setCurrentPrincipal(loanStateHistory.getEndingPrincipal());
		finModel.setCurrentInterest(loanStateHistory.getLoanStateAsOf(systemDate).getInterestThrough(systemDate));
		finModel.setCurrentFees(loanStateHistory.getEndingFees());
		finModel.setBaseRate(loanStateHistory.getEndingBaseRate());
		finModel.setMargin(loanStateHistory.getEndingMargin());
		finModel.setEffectiveIntRate(finModel.getBaseRate().add(finModel.getMargin()));
		finModel.setDailyInterestAmount(loanStateHistory.getEndingInterestRate().multiply(BigDecimal.valueOf(loanStateHistory.getEndingPrincipal())).divide(BigDecimal.valueOf(Constants.DAYS_IN_YEAR), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE));
		finModel.setMinimumPaymentAmount(amortizationLogic.findPaymentAmountForDate(loan,systemSettings.getCurrentSystemDate()));
		LoanStatementSummary statementSummary = statementRepository.getLoanStatementSummaryForLoan(loan);
		finModel.setNextDueDate(statementSummary.getNextDueDate());
		LoanPayment lastPayment = loanPaymentRepository.findMostRecentLoanPayment(loan.getLoanID());
		if(lastPayment != null){
			finModel.setLastPaidDate(lastPayment.getPayment().getEffectiveDate());
		}
		Date repaymentStartDate = repaymentStartCalculator.calculateRepaymentStartDateForAccount(loan.getAccount());
		finModel.setRepaymentStartDate(repaymentStartDate);
		finModel.setFirstDueDate(loan.getFirstDueDate());
		finModel.setInitialDueDate(loan.getInitialDueDate());
		int remainingTerm = loanTermCalculator.calculateRemainingLoanTermAsOf(loan, systemDate);
		LoanProgramSettings loanSettings = loanProgramSettingsRepository.findEffectiveLoanProgramSettingsForLoan(loan);
		finModel.setUsedTerm(loanSettings.getMaximumLoanTerm()-remainingTerm);
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
