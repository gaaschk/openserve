package org.gsoft.openserv.buslogic.amortization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.amortization.AmortizationLoanPayment;
import org.gsoft.openserv.domain.amortization.AmortizationSchedule;
import org.gsoft.openserv.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.domain.loan.Disbursement;
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
	
	public List<AmortizationSchedule> createAmortizationSchedule(List<Long> loanIDs, List<Date> effectiveDates){
		ArrayList<AmortizationSchedule> schedules = new ArrayList<>();
		for(Date date:effectiveDates){
			AmortizationSchedule sched = this.createAmortizationSchedule(loanIDs, date);
			schedules.add(sched);
		}
		return schedules;
	}
	
	public AmortizationSchedule createAmortizationSchedule(List<Long> loanIDs, Date effectiveDate){
		ArrayList<LoanAmortizationSchedule> newLoanAmortizations = new ArrayList<>();
		for(Long loanID:loanIDs){
			Loan loan = loanRepository.findOne(loanID);
			LoanAmortizationSchedule loanAmortizationSchedule = this.createLoanAmortizationSchedule(loan, effectiveDate);
			if(loanAmortizationSchedule != null && loanAmortizationSchedule.getAmortizationSchedule() == null){
				newLoanAmortizations.add(loanAmortizationSchedule);
			}
		}
		AmortizationSchedule amortizationSchedule = null;
		if(newLoanAmortizations.size()>0){
			amortizationSchedule = new AmortizationSchedule();
			amortizationSchedule = amortizationScheduleRepository.save(amortizationSchedule);
			amortizationSchedule.setCreationDate(new Date());
			amortizationSchedule.setEffectiveDate(effectiveDate);
			for(LoanAmortizationSchedule loanSched:newLoanAmortizations){
				amortizationSchedule.addLoanAmortizationSchedule(loanSched);
			}
		}
		for(Long loanID:loanIDs){
			Loan loan = loanRepository.findOne(loanID);
			loan.setCurrentAmortizationSchedule(amortizationScheduleRepository.findScheduleForLoan(loanID));
		}
		return amortizationSchedule;
	}
	
	public List<Date> getMissingAmortizationScheduleDates(List<Long> loanIDs){
		ArrayList<Date> missingAmortizationDates = new ArrayList<>();
		for(Long loanID:loanIDs){
			Loan loan = loanRepository.findOne(loanID);
			List<LoanAmortizationSchedule> existingScheds = amortizationScheduleRepository.findAllSchedulesForLoan(loanID);
			Date repayStartDate = loan.getRepaymentStartDate();
			if(!scheduleExists(existingScheds, repayStartDate)){
				missingAmortizationDates.add(repayStartDate);
			}
			for(Disbursement disb:loan.getDisbursements()){
				if(disb.getDisbursementEffectiveDate().after(repayStartDate) && 
						!scheduleExists(existingScheds, disb.getDisbursementEffectiveDate()) && 
						!missingAmortizationDates.contains(disb.getDisbursementEffectiveDate())){
					missingAmortizationDates.add(disb.getDisbursementEffectiveDate());
				}
			}
		}
		return missingAmortizationDates;
	}
	
	private boolean scheduleExists(List<LoanAmortizationSchedule> lams, Date date){
		for(LoanAmortizationSchedule lam:lams){
			if(lam.getAmortizationSchedule().getEffectiveDate().compareTo(date) == 0){
				return true;
			}
		}
		return false;
	}
	
	private LoanAmortizationSchedule createLoanAmortizationSchedule(Loan loan, Date effectiveDate){
		LoanAmortizationSchedule loanAmortizationSchedule = amortizationScheduleRepository.findScheduleForLoanWithEffectiveDate(loan.getLoanID(),effectiveDate);
		if(loanAmortizationSchedule==null){
			LoanRateValue lrv = loanRateValueRepository.findLoanRateValueForLoanAsOf(loan.getLoanID(), effectiveDate);
			if(lrv != null){
				BigDecimal margin = lrv.getMarginValue();
				BigDecimal rate = lrv.getRateValue().getRateValue();
				BigDecimal annualInterestRate = margin.add(rate);
				int remainingTerm = loan.getRemainingLoanTermAsOf(effectiveDate);	
				LoanStateHistory loanHistory =  loanStateHistoryRepo.findLoanStateHistoryAsOf(loan, effectiveDate);
				int principal = loanHistory.getEndingPrincipal();
				Integer paymentAmount = PaymentAmountCalculator.calculatePaymentAmount(principal,annualInterestRate,remainingTerm);
				BigDecimal exactPayment = PaymentAmountCalculator.calculatePaymentAmountExact(principal,annualInterestRate,remainingTerm);
				BigDecimal loanTotalCost = exactPayment.multiply(BigDecimal.valueOf(remainingTerm));
				int regularPaymentCount = remainingTerm-1;
				int lastPaymentAmount = (int)(loanTotalCost.longValue() - (long)(paymentAmount * remainingTerm));
				loanAmortizationSchedule = new LoanAmortizationSchedule();
				loanAmortizationSchedule.setLoanID(loan.getLoanID());
				AmortizationLoanPayment regularPayment = new AmortizationLoanPayment();
				regularPayment.setPaymentOrder(1);
				regularPayment.setPaymentAmount(paymentAmount);
				regularPayment.setPaymentCount(regularPaymentCount);
				loanAmortizationSchedule.addAmortizationLoanPayment(regularPayment);
				AmortizationLoanPayment lastPayment = new AmortizationLoanPayment();
				lastPayment.setPaymentOrder(2);
				lastPayment.setPaymentAmount(lastPaymentAmount+paymentAmount);
				lastPayment.setPaymentCount(1);
				loanAmortizationSchedule.addAmortizationLoanPayment(lastPayment);
			}
		}
		return loanAmortizationSchedule;
	}
	
	public Integer findPaymentAmountForDate(Loan loan, Date asOfDate){
		int minPaymentAmount = 0;
		LoanAmortizationSchedule effectiveAmortization = amortizationScheduleRepository.findScheduleForLoanEffectiveOn(loan.getLoanID(), asOfDate);
		if(effectiveAmortization != null){
			List<AmortizationLoanPayment> payments = effectiveAmortization.getAmortizationLoanPayments();
			Collections.sort(payments, new Comparator<AmortizationLoanPayment>(){
				@Override
				public int compare(AmortizationLoanPayment p1, AmortizationLoanPayment p2){
					return p1.getPaymentOrder() - p2.getPaymentOrder();
				}
			});
			int remainingTerm = loan.getRemainingLoanTermAsOf(asOfDate);
			int totalTerm = 0;
			for(AmortizationLoanPayment amortPayment:payments){
				totalTerm+=amortPayment.getPaymentCount();
			}
			int usedTerm = totalTerm - remainingTerm;
			int index = 0;
			int paymentCount = payments.get(index).getPaymentCount();
			while(paymentCount < usedTerm){
				paymentCount += payments.get(index).getPaymentCount();
				index++;
			}
			minPaymentAmount = payments.get(index).getPaymentAmount(); 
		}
		return minPaymentAmount; 
	}
}
