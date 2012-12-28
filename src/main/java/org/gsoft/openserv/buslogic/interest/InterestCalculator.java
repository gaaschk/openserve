package org.gsoft.openserv.buslogic.interest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanBalanceAdjustment;
import org.gsoft.openserv.repositories.LoanRateValueRepository;
import org.gsoft.openserv.repositories.loan.LoanBalanceAdjustmentRepository;
import org.gsoft.openserv.util.Constants;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

@Component
public class InterestCalculator {
	@Resource
	private LoanBalanceAdjustmentRepository loanBalanceAdjustmentRepo;
	@Resource
	private LoanRateValueRepository loanRateValueRepo;
	
	
	public BigDecimal calculateLoanInterestAmountForPeriod(Loan loan, Date fromDate, Date toDate){
		List<LoanBalanceAdjustment> principalChanges = loanBalanceAdjustmentRepo.findAllPrincipalChangesFromDateToDate(loan.getLoanID(), fromDate, toDate);
		List<LoanRateValue> loanRateValues = loanRateValueRepo.findAllLoanRateValuesFromDateToDate(loan.getLoanID(), fromDate, toDate);
		Collections.sort(principalChanges, new Comparator<LoanBalanceAdjustment>(){
			@Override
			public int compare(LoanBalanceAdjustment o1,
					LoanBalanceAdjustment o2) {
				if(o1 == o2){
					return 0;
				}
				else if(o1 == null){
					return -1;
				}
				else if(o2 == null){
					return 1;
				}
				else if(o1.getEffectiveDate() == o2.getEffectiveDate()){
					return 0;
				}
				else if(o1.getEffectiveDate() == null){
					return -1;
				}
				else if(o2.getEffectiveDate() == null){
					return 1;
				}
				return o1.getEffectiveDate().compareTo(o2.getEffectiveDate());
			}
		});
		Collections.sort(loanRateValues, new Comparator<LoanRateValue>(){
			@Override
			public int compare(LoanRateValue o1,
					LoanRateValue o2) {
				if(o1 == o2){
					return 0;
				}
				else if(o1 == null){
					return -1;
				}
				else if(o2 == null){
					return 1;
				}
				else if(o1.getLockedDate() == o2.getLockedDate()){
					return 0;
				}
				else if(o1.getLockedDate() == null){
					return -1;
				}
				else if(o2.getLockedDate() == null){
					return 1;
				}
				return o1.getLockedDate().compareTo(o2.getLockedDate());
			}
		});

		int rateIndex = 0, princIndex = 0;
		DateTime rangeBegDate = (fromDate.before(loan.getServicingStartDate()))?new DateTime(loan.getServicingStartDate()):new DateTime(fromDate);
		int principal = 0;
		if(rangeBegDate.isAfter(loan.getServicingStartDate().getTime())){
			principal = loanBalanceAdjustmentRepo.findNetPrincipalChangeThruDate(loan.getLoanID(), rangeBegDate.minusDays(1).toDate());
		}
		BigDecimal rate = loanRateValueRepo.findLoanRateValueForLoanAsOf(loan.getLoanID(), rangeBegDate.toDate()).getRateValue().getRateValue();
		BigDecimal dailyIntAmount = rate.multiply(BigDecimal.valueOf(principal)).divide(BigDecimal.valueOf(Constants.DAYS_IN_YEAR), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		BigDecimal totalInterest = BigDecimal.ZERO;
		DateTime rangeEndDate = null;
		boolean keepLooping = true;
		while(keepLooping){
			LoanRateValue lrv = (rateIndex >= loanRateValues.size())?null:loanRateValues.get(rateIndex);
			LoanBalanceAdjustment principalChange = (princIndex >= principalChanges.size())?null:principalChanges.get(princIndex);
			Date rateChangeDate = (lrv == null)?null:lrv.getLockedDate();
			Date prinChangeDate = (principalChange == null)?null:principalChange.getEffectiveDate();
			if(rateChangeDate == null && prinChangeDate == null){
				rangeEndDate = new DateTime(toDate);
				keepLooping = false;
			}
			else{
				if(rateChangeDate == null || (prinChangeDate != null && (rateChangeDate.compareTo(prinChangeDate) >= 0))){
					rangeEndDate = new DateTime(principalChange.getEffectiveDate());
					principal += (principalChange == null)?0:principalChange.getPrincipalChange();
					princIndex++;
				}
				if(principalChange == null || (rateChangeDate != null && (prinChangeDate.compareTo(rateChangeDate) >= 0))){
					rangeEndDate = new DateTime(loanRateValues.get(rateIndex).getLockedDate());
					rate = (lrv == null)?rate:lrv.getRateValue().getRateValue();
					rateIndex++;
				}
			}
			int days = Days.daysBetween(rangeBegDate, rangeEndDate).getDays();
			totalInterest = totalInterest.add(dailyIntAmount.multiply(BigDecimal.valueOf(days)));
			rangeBegDate = rangeEndDate;
			dailyIntAmount = rate.divide(BigDecimal.valueOf(Constants.DAYS_IN_YEAR), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE).multiply(BigDecimal.valueOf(principal));
		}
		totalInterest = totalInterest.setScale(Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		return totalInterest;
	}

	
	public BigDecimal getInterestRateForLoan(Loan loan){
		return this.loanRateValueRepo.findMostRecentLoanRateValueForLoan(loan.getLoanID()).getRateValue().getRateValue();
	}
}
