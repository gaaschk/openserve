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
import org.gsoft.openserv.domain.loan.LoanProgramSettings;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.repositories.loan.LoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.rates.LoanRateValueRepository;
import org.gsoft.openserv.repositories.rates.RateValueRepository;
import org.gsoft.openserv.util.comparator.ComparatorAdapter;
import org.gsoft.openserv.util.time.FrequencyType;
import org.springframework.stereotype.Component;

@Component
public class LoanInterestRateFactory {
	@Resource
	private LoanProgramSettingsRepository loanProgramSettingsRepo;
	@Resource
	private RateValueRepository rateValueRepository;
	@Resource
	private LoanRateValueRepository loanRateValueRepo;
	@Resource
	private SystemSettingsLogic systemSettignsLogic;
	private Comparator<LoanProgramSettings> loanProgramSettingsComparator;
	
	private Comparator<LoanProgramSettings> getLoanProgramSettingsComparator(){
		if(this.loanProgramSettingsComparator == null){
			BeanComparator comp1 = new BeanComparator("effectiveDate");
			loanProgramSettingsComparator = new ComparatorAdapter<>(comp1);
		}
		return loanProgramSettingsComparator;
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
	
	private List<Date> findExpectedLoanRateDatesForPeriod(Loan loan, Date start, Date end, LoanProgramSettings settings){
		FrequencyType frequency = settings.getBaseRateUpdateFrequency();
		List<Date> dates = frequency.findAllDatesBetween(start, end);
		return dates;
	}
	
	private List<Date> findAllRateChangeDates(Loan loan){
		List<LoanProgramSettings> lps = loanProgramSettingsRepo.findAllLoanProgramSettingsByLenderIDAndLoanProgramAndEffectiveDate(loan.getLenderID(), loan.getLoanProgram(), loan.getServicingStartDate());
		Collections.sort(lps, this.getLoanProgramSettingsComparator());
		Date sysDate = systemSettignsLogic.getCurrentSystemDate();
		Date startDate = loan.getServicingStartDate();
		List<Date> dates = new ArrayList<>();
		dates.add(loan.getServicingStartDate());
		if(lps.size()==1){
			dates.addAll(this.findExpectedLoanRateDatesForPeriod(loan, startDate, sysDate, lps.get(0)));
		}
		else{
			LoanProgramSettings lastSettings = null;
			for(LoanProgramSettings settings:lps){
				lastSettings = settings;
				if(settings.getEffectiveDate().after(startDate)){
					dates.addAll(this.findExpectedLoanRateDatesForPeriod(loan, startDate, settings.getEffectiveDate(), settings));
					startDate = settings.getEffectiveDate();
				}
			}
			dates.addAll(this.findExpectedLoanRateDatesForPeriod(loan, startDate, sysDate, lastSettings));
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
		LoanProgramSettings settings = loanProgramSettingsRepo.findEffectiveLoanProgramSettingsForLoan(loan);
		RateValue quote = rateValueRepository.findCurrentRateAsOf(settings.getBaseRate(), loan.getServicingStartDate());
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
			LoanProgramSettings settings = loanProgramSettingsRepo.findLoanProgramSettingsByLenderIDAndLoanProgramAndEffectiveDate(loan.getLenderID(), loan.getLoanProgram(), date);
			Rate rate = settings.getBaseRate();
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
