package org.gsoft.openserv.buslogic.amortization;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.amortization.AmortizationLoanPayment;
import org.gsoft.openserv.domain.amortization.AmortizationSchedule;
import org.gsoft.openserv.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.gsoft.openserv.repositories.amortization.AmortizationScheduleRepository;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.repositories.loan.LoanStateHistoryRepository;
import org.gsoft.openserv.repositories.rates.LoanRateValueRepository;
import org.springframework.stereotype.Component;

@Component
public class AmortizationLogic {
	@Resource
	private PaymentAmountCalculator paymentCalculator;
	@Resource
	private AmortizationScheduleRepository amortizationScheduleRepository;
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private LoanRateValueRepository loanRateValueRepository;
	@Resource
	private LoanStateHistoryRepository loanStateHistoryRepo;
	
	public AmortizationSchedule createAmortizationSchedule(List<Long> loanIDs, Date effectiveDate){
		AmortizationSchedule amortizationSchedule = new AmortizationSchedule();
		for(Long loanID:loanIDs){
			amortizationSchedule.addLoanAmortizationSchedule(this.createLoanAmortizationSchedule(loanRepository.findOne(loanID)));
		}
		amortizationSchedule.setCreationDate(new Date());
		amortizationSchedule.setEffectiveDate(effectiveDate);
		amortizationScheduleRepository.save(amortizationSchedule);
		return amortizationSchedule;
	}
	
	private LoanAmortizationSchedule createLoanAmortizationSchedule(Loan loan){
		LoanRateValue lrv = loanRateValueRepository.findMostRecentLoanRateValueForLoan(loan.getLoanID());
		BigDecimal margin = lrv.getMarginValue();
		BigDecimal rate = lrv.getRateValue().getRateValue();
		BigDecimal annualInterestRate = margin.add(rate);
		LoanStateHistory loanHistory =  loanStateHistoryRepo.findLoanStateHistory(loan);
		Integer paymentAmount = paymentCalculator.calculatePaymentAmount(loanHistory.getEndingPrincipal(), annualInterestRate, loan.getRemainingLoanTerm());
		int regularPaymentCount = loanHistory.getEndingPrincipal()/paymentAmount;
		int lastPaymentAmount = loanHistory.getEndingPrincipal() - (paymentAmount*regularPaymentCount);
		LoanAmortizationSchedule loanAmortizationSchedule = new LoanAmortizationSchedule();
		loanAmortizationSchedule.setLoanID(loan.getLoanID());
		AmortizationLoanPayment regularPayment = new AmortizationLoanPayment();
		regularPayment.setPaymentAmount(paymentAmount);
		regularPayment.setPaymentCount(regularPaymentCount);
		loanAmortizationSchedule.addAmortizationLoanPayment(regularPayment);
		AmortizationLoanPayment lastPayment = new AmortizationLoanPayment();
		lastPayment.setPaymentAmount(lastPaymentAmount);
		lastPayment.setPaymentCount(1);
		loanAmortizationSchedule.addAmortizationLoanPayment(lastPayment);
		loan.setMinimumPaymentAmount(paymentAmount);
		return loanAmortizationSchedule;
	}
}
