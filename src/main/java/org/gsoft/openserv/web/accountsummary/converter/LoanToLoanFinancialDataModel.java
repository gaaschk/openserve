package org.gsoft.openserv.web.accountsummary.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.amortization.AmortizationLogic;
import org.gsoft.openserv.buslogic.amortization.LoanTermCalculator;
import org.gsoft.openserv.buslogic.repayment.RepaymentStartDateCalculator;
import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgramSettings;
import org.gsoft.openserv.domain.loan.loanstate.LoanStateHistory;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.billing.LoanStatementSummary;
import org.gsoft.openserv.repositories.loan.LoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.payment.LoanPaymentRepository;
import org.gsoft.openserv.repositories.payment.LoanStatementRepository;
import org.gsoft.openserv.service.AccountSummaryService;
import org.gsoft.openserv.util.Constants;
import org.gsoft.openserv.util.ListUtility;
import org.gsoft.openserv.web.accountsummary.model.LoanFinancialDataModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanToLoanFinancialDataModel implements Converter<Loan, LoanFinancialDataModel>{
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

	@Override
	public LoanFinancialDataModel convert(Loan loan) {
		Date systemDate = systemSettings.getCurrentSystemDate();
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
		List<LoanPayment> allPayments = ListUtility.addAll(new ArrayList<LoanPayment>(), loanPaymentRepository.findAllLoanPayments(loan.getLoanID())); 
		LoanPayment lastPayment = null;
		if(allPayments != null && allPayments.size()>0)
			lastPayment = allPayments.get(allPayments.size()-1);
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
		return finModel;
	}

	
}
