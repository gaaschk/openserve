package org.gsoft.openserv.buslogic.amortization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.repayment.RepaymentStartDateCalculator;
import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.amortization.AmortizationLoanPayment;
import org.gsoft.openserv.domain.amortization.AmortizationSchedule;
import org.gsoft.openserv.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.domain.loan.Account;
import org.gsoft.openserv.domain.loan.Disbursement;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.loanstate.LoanStateHistory;
import org.gsoft.openserv.domain.loan.loanstate.LoanStateHistoryBuilder;
import org.gsoft.openserv.domain.loan.loanstate.LoanStateHistoryBuilderFactoryBean;
import org.gsoft.openserv.repositories.account.AccountRepository;
import org.gsoft.openserv.repositories.amortization.AmortizationScheduleRepository;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.repositories.rates.LoanRateValueRepository;
import org.gsoft.openserv.util.ListUtility;
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
	private SystemSettingsLogic systemSettings;
	@Resource
	private AccountRepository accountRepository;
	@Resource
	private RepaymentStartDateCalculator repaymentStartDateCalculator;
	@Resource
	private LoanTermCalculator loanTermCalculator;
	@Resource
	private LoanStateHistoryBuilderFactoryBean loanStateBuilderFactory;
	
	public boolean invalidAmortizationSchedulesExist(Long accountID, Date asOfDate){
		return this.findInvalidAmortizationSchedules(accountID, asOfDate).size() > 0;
	}
	
	public boolean amortizationSchedulesMissing(Long accountID, Date asOfDate){
		return this.getMissingAmortizationScheduleDates(accountID, asOfDate).size() > 0;
	}
	
	public List<AmortizationSchedule> updateInvalidAmortizationSchedules(Long accountID, Date asOfDate){
		List<AmortizationSchedule> invalidSchedules = this.findInvalidAmortizationSchedules(accountID, asOfDate);
		for(AmortizationSchedule schedule:invalidSchedules){
			schedule.setInvalid(true);
			amortizationScheduleRepository.save(schedule);
		}
		return invalidSchedules;
	}
	
	public List<AmortizationSchedule> createMissingAmortizationSchedules(Long accountID, Date asOfDate){
		List<Date> missingDates = this.getMissingAmortizationScheduleDates(accountID, asOfDate);
		ArrayList<AmortizationSchedule> newSchedules = new ArrayList<>();
		for(Date effectiveDate:missingDates){
			AmortizationSchedule newSchedule = this.createAmortizationSchedule(accountID, effectiveDate);
			amortizationScheduleRepository.save(newSchedule);
			newSchedules.add(newSchedule);
		}
		return newSchedules;
	}
	
	private AmortizationSchedule createAmortizationSchedule(Long accountID, Date effectiveDate){
		AmortizationSchedule amortSched = null; 
		List<Loan> loans = loanRepository.findAllByAccountID(accountID);
		ArrayList<LoanAmortizationSchedule> lams = new ArrayList<>();
		for(Loan loan:loans){
			LoanAmortizationSchedule lam = this.createLoanAmortizationSchedule(loan, effectiveDate);
			lams.add(lam);
		}
		if(lams.size()>0){
			amortSched = new AmortizationSchedule();
			amortSched.setAccountID(accountID);
			amortSched.setCreationDate(systemSettings.getCurrentSystemDate());
			amortSched.setEffectiveDate(effectiveDate);
			amortSched.setInvalid(false);
			amortSched.setLoanAmortizationSchedules(lams);
			for(LoanAmortizationSchedule lam:lams){
				if(lam!=null)
					lam.setAmortizationSchedule(amortSched);
			}
		}
		return amortSched;
	}
	
	private List<Date> findExpectedAmortizationDates(Long accountID, Date throughDate){
		Set<Date> amortizationDates = new TreeSet<>();
		Account account = accountRepository.findOne(accountID);
		Date accountRepayStart = repaymentStartDateCalculator.calculateRepaymentStartDateForAccount(account);
		amortizationDates.add(accountRepayStart);
		List<Loan> loans = loanRepository.findAllByAccountID(accountID);
		for(Loan loan:loans){
			for(Disbursement disb:loan.getDisbursements()){
				if(!disb.getDisbursementEffectiveDate().before(accountRepayStart)){
					amortizationDates.add(disb.getDisbursementEffectiveDate());
				}
			}
			List<LoanRateValue> loanRateValues = loanRateValueRepository.findAllLoanRateValuesThruDate(loan.getLoanID(), throughDate);
			for(LoanRateValue lrv:loanRateValues){
				if(!lrv.getLockedDate().before(accountRepayStart)){
					amortizationDates.add(lrv.getLockedDate());
				}
			}
		}
		return ListUtility.addAll(new ArrayList<Date>(), amortizationDates);
	}
	
	private List<AmortizationSchedule> findInvalidAmortizationSchedules(Long accountID, Date throughDate){
		List<Date> dates = this.findExpectedAmortizationDates(accountID, throughDate);
		List<AmortizationSchedule> schedules = amortizationScheduleRepository.findAllSchedulesForAccountThroughDate(accountID, throughDate);
		List<Loan> accountLoans = loanRepository.findAllByAccountID(accountID);
		ArrayList<AmortizationSchedule> invalidSchedules = new ArrayList<>();
		for(AmortizationSchedule ams:schedules){
			if(!containsDate(dates, ams.getEffectiveDate()) || ams.getLoanAmortizationSchedules().size() != accountLoans.size()){
				invalidSchedules.add(ams);
			}
		}
		return invalidSchedules;
	}
	
	private boolean containsDate(List<Date> dates, Date date){
		for(Date aDate:dates){
			if(aDate.compareTo(date)==0)
				return true;
		}
		return false;
	}
	
	private List<Date> getMissingAmortizationScheduleDates(Long accountID, Date throughDate){
		List<Date> dates = this.findExpectedAmortizationDates(accountID, throughDate);
		List<AmortizationSchedule> schedules = amortizationScheduleRepository.findAllSchedulesForAccountThroughDate(accountID, throughDate);
		ArrayList<Date> missingDates = new ArrayList<>();
		for(Date date:dates){
			if(!scheduleExists(schedules, date)){
				missingDates.add(date);
			}
		}
		return missingDates;
	}
	
	private boolean scheduleExists(List<AmortizationSchedule> ams, Date date){
		for(AmortizationSchedule am:ams){
			if(am.getEffectiveDate().compareTo(date) == 0){
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
				int remainingTerm = loanTermCalculator.calculateRemainingLoanTermAsOf(loan, effectiveDate); 
				LoanStateHistoryBuilder loanStateHistoryBuilder = loanStateBuilderFactory.createBuilder();
				loanStateHistoryBuilder.buildLoanStateHistoryForLoanAsOf(loan, effectiveDate);
				LoanStateHistory loanHistory =  loanStateHistoryBuilder.getLoanStateHistory();
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
			int remainingTerm = loanTermCalculator.calculateRemainingLoanTermAsOf(loan, asOfDate); 
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
