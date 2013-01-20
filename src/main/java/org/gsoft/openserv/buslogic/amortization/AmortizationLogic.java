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
			Loan loan = loanRepository.findOne(loanID);
			LoanAmortizationSchedule loanAmortizationSchedule = this.createLoanAmortizationSchedule(loan, effectiveDate);
			if(loanAmortizationSchedule != null){
				amortizationSchedule.addLoanAmortizationSchedule(loanAmortizationSchedule);
			}
		}
		amortizationSchedule.setCreationDate(new Date());
		amortizationSchedule.setEffectiveDate(effectiveDate);
		amortizationScheduleRepository.save(amortizationSchedule);
		return amortizationSchedule;
	}
	
	private LoanAmortizationSchedule createLoanAmortizationSchedule(Loan loan, Date effectiveDate){
		LoanAmortizationSchedule loanAmortizationSchedule = null;
		LoanRateValue lrv = loanRateValueRepository.findMostRecentLoanRateValueForLoan(loan.getLoanID());
		if(lrv != null){
			BigDecimal margin = lrv.getMarginValue();
			BigDecimal rate = lrv.getRateValue().getRateValue();
			BigDecimal annualInterestRate = margin.add(rate);
			LoanStateHistory loanHistory =  loanStateHistoryRepo.findLoanStateHistory(loan);
		
			Integer paymentAmount = PaymentAmountCalculator.calculatePaymentAmount(loanHistory.getEndingPrincipal(), annualInterestRate, loan.getRemainingLoanTermAsOf(effectiveDate));
			int regularPaymentCount = loanHistory.getEndingPrincipal()/paymentAmount;
			int lastPaymentAmount = loanHistory.getEndingPrincipal() - (paymentAmount*regularPaymentCount);
			loanAmortizationSchedule = new LoanAmortizationSchedule();
			loanAmortizationSchedule.setLoanID(loan.getLoanID());
			loan.setCurrentAmortizationSchedule(loanAmortizationSchedule);
			AmortizationLoanPayment regularPayment = new AmortizationLoanPayment();
			regularPayment.setPaymentOrder(1);
			regularPayment.setPaymentAmount(paymentAmount);
			regularPayment.setPaymentCount(regularPaymentCount);
			loanAmortizationSchedule.addAmortizationLoanPayment(regularPayment);
			AmortizationLoanPayment lastPayment = new AmortizationLoanPayment();
			lastPayment.setPaymentOrder(1);
			lastPayment.setPaymentAmount(lastPaymentAmount);
			lastPayment.setPaymentCount(1);
			loanAmortizationSchedule.addAmortizationLoanPayment(lastPayment);
		}
		return loanAmortizationSchedule;
	}
}
