package org.gsoft.openserv.buslogic.interest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanComparator;
import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.gsoft.openserv.repositories.rates.LoanRateValueRepository;
import org.gsoft.openserv.repositories.rates.RateValueRepository;
import org.gsoft.openserv.util.comparator.ComparatorAdapter;
import org.gsoft.openserv.util.time.FrequencyType;
import org.springframework.stereotype.Component;

@Component
public class LoanInterestRateFactory {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepo;
	@Resource
	private RateValueRepository rateValueRepository;
	@Resource
	private LoanRateValueRepository loanRateValueRepo;
	@Resource
	private SystemSettingsLogic systemSettignsLogic;
	private Comparator<LoanTypeProfile> loanTypeProfileComparator;
	
	private Comparator<LoanTypeProfile> getLoanTypeProfileComparator(){
		if(this.loanTypeProfileComparator == null){
			BeanComparator comp1 = new BeanComparator("effectiveDate");
			loanTypeProfileComparator = new ComparatorAdapter<>(comp1);
		}
		return loanTypeProfileComparator;
	}
	
	public boolean isLoanRateUpdateNeeded(Loan loan){
		boolean updateNeeded = false;
		List<Date> allDates = this.findAllRateChangeDates(loan);
		List<LoanRateValue> allLRVs = loanRateValueRepo.findAllLoanRateValues(loan.getLoanID());
		List<Date> missingDates = this.findMissingLoanRateDates(allLRVs,allDates);
		if(missingDates != null && missingDates.size() > 0){
			updateNeeded = true;
		}
		return updateNeeded;
		
	}
	
	private List<Date> findExpectedLoanRateDatesForPeriod(Loan loan, Date start, Date end, LoanTypeProfile ltp){
		FrequencyType frequency = ltp.getBaseRateUpdateFrequency();
		List<Date> dates = frequency.findAllDatesBetween(start, end);
		return dates;
	}
	
	private List<Date> findAllRateChangeDates(Loan loan){
		List<LoanTypeProfile> ltps = loanTypeProfileRepo.findLoanTypeProfilesByLoanTypeAndEffectiveDate(loan.getLoanType(), loan.getServicingStartDate());
		Collections.sort(ltps, this.getLoanTypeProfileComparator());
		Date sysDate = systemSettignsLogic.getCurrentSystemDate();
		Date startDate = loan.getServicingStartDate();
		List<Date> dates = new ArrayList<>();
		dates.add(loan.getServicingStartDate());
		if(ltps.size()==1){
			dates.addAll(this.findExpectedLoanRateDatesForPeriod(loan, startDate, sysDate, ltps.get(0)));
		}
		else{
			LoanTypeProfile lastLtp = null;
			for(LoanTypeProfile ltp:ltps){
				lastLtp = ltp;
				if(ltp.getEffectiveDate().after(startDate)){
					dates.addAll(this.findExpectedLoanRateDatesForPeriod(loan, startDate, ltp.getEffectiveDate(), ltp));
					startDate = ltp.getEffectiveDate();
				}
			}
			dates.addAll(this.findExpectedLoanRateDatesForPeriod(loan, startDate, sysDate, lastLtp));
		}
		return dates;
	}

	private boolean containsDate(List<LoanRateValue> LRVs, Date aDate){
		for(LoanRateValue lrv:LRVs){
			if(lrv.getLockedDate().compareTo(aDate) == 0)
				return true;
		}
		return false;
	}
	
	private List<Date> findMissingLoanRateDates(List<LoanRateValue> lrvs, List<Date> expectedDates){
		List<Date> missingDates = new ArrayList<>();
		for(Date expectedDate:expectedDates){
			if(!containsDate(lrvs, expectedDate))
				missingDates.add(expectedDate);
		}
		return missingDates;
	}
	
	private boolean containsLRVDate(LoanRateValue lrv, List<Date> expectedDates){
		for(Date aDate:expectedDates){
			if(aDate.compareTo(lrv.getLockedDate()) == 0)
				return true;
		}
		return false;
	}
	
	private List<LoanRateValue> findInvalidLoanRates(List<LoanRateValue> lrvs, List<Date> expectedDates){
		List<LoanRateValue> invalidLRVs = new ArrayList<>();
		for(LoanRateValue lrv:lrvs){
			if(!containsLRVDate(lrv, expectedDates))
				invalidLRVs.add(lrv);
		}
		return invalidLRVs;
	}
	
	public BigDecimal getBaseRateForLoan(Loan loan){
		LoanTypeProfile ltp = loan.getEffectiveLoanTypeProfile();
		RateValue quote = rateValueRepository.findCurrentRateAsOf(ltp.getBaseRate(), loan.getServicingStartDate());
		if(quote != null){
			return quote.getRateValue();
		}
		return BigDecimal.ZERO;
	}
	
	public LoanRateValue updateRateForLoan(Loan loan){
		List<Date> allDates = this.findAllRateChangeDates(loan);
		List<LoanRateValue> allLRVs = loanRateValueRepo.findAllLoanRateValues(loan.getLoanID());

		List<Date> missingDates = this.findMissingLoanRateDates(allLRVs,allDates);
		LoanRateValue lastRate = null;
		for(Date date:missingDates){
			LoanTypeProfile ltp = loanTypeProfileRepo.findLoanTypeProfileByLoanTypeAndEffectiveDate(loan.getLoanType(), date);
			Rate rate = ltp.getBaseRate();
			RateValue rateValue = rateValueRepository.findRateValue(rate, date);
			LoanRateValue loanRateValue = new LoanRateValue();
			loanRateValue.setLockedDate(date);
			loanRateValue.setMarginValue(BigDecimal.ZERO);
			loanRateValue.setRateValue(rateValue);
			loanRateValue.setLoanID(loan.getLoanID());
			loanRateValue = loanRateValueRepo.save(loanRateValue);
			lastRate = loanRateValue;
		}
		
		List<LoanRateValue> invalidLRVs = this.findInvalidLoanRates(allLRVs, allDates);
		for(LoanRateValue lrv:invalidLRVs){
			loanRateValueRepo.delete(lrv);
		}
		return lastRate;
	}
}
