package org.gsoft.phoenix.buslogic.amortization;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.interest.InterestAccrualLogic;
import org.gsoft.phoenix.domain.amortization.AmortizationLoanPayment;
import org.gsoft.phoenix.domain.amortization.AmortizationSchedule;
import org.gsoft.phoenix.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.amortization.AmortizationScheduleRepository;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.springframework.stereotype.Component;

@Component
public class AmortizationLogic {
	@Resource
	private PaymentAmountCalculator paymentCalculator;
	@Resource
	private InterestAccrualLogic interestLogic;
	@Resource
	private AmortizationScheduleRepository amortizationScheduleRepository;
	@Resource
	private LoanRepository loanRepository;
	
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
		BigDecimal annualInterestRate = interestLogic.getInterestRateForLoan(loan);
		Integer paymentAmount = paymentCalculator.calculatePaymentAmount(loan.getCurrentPrincipal(), annualInterestRate, loan.getRemainingLoanTerm());
		int regularPaymentCount = loan.getCurrentPrincipal()/paymentAmount;
		int lastPaymentAmount = loan.getCurrentPrincipal() - (paymentAmount*regularPaymentCount);
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
