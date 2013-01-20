package org.gsoft.openserv.buslogic.interest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.gsoft.openserv.repositories.rates.LoanRateValueRepository;
import org.gsoft.openserv.repositories.rates.RateValueRepository;
import org.gsoft.openserv.util.time.FrequencyType;
import org.springframework.stereotype.Component;

@Component
public class LoanInterestRateFactory {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileLogic;
	@Resource
	private RateValueRepository rateValueRepository;
	@Resource
	private LoanRateValueRepository loanRateValueRepo;
	@Resource
	private SystemSettingsLogic systemSettignsLogic;
	
	public boolean isLoanRateUpdateNeeded(Loan loan){
		boolean updateNeeded = false;
		List<Date> missingDates = this.findMissingLoanRateDates(loan);
		if(missingDates == null || missingDates.size() > 0){
			updateNeeded = true;
		}
		return updateNeeded;
		
	}
	
	private List<Date> findMissingLoanRateDates(Loan loan){
		LoanTypeProfile ltp = loan.getEffectiveLoanTypeProfile();
		LoanRateValue lrv = loanRateValueRepo.findMostRecentLoanRateValueForLoan(loan.getLoanID());
		ArrayList<Date> dates = new ArrayList<>();
		Date lastUpdateDate = null;
		if(lrv == null){
			dates.add(loan.getServicingStartDate());
			lastUpdateDate = loan.getServicingStartDate();
		}
		else{
			lastUpdateDate = lrv.getLockedDate();
		}
		FrequencyType frequency = ltp.getBaseRateUpdateFrequency();
		Date systemDate = systemSettignsLogic.getCurrentSystemDate();
		if(frequency != null){
			dates.addAll(frequency.findAllDatesBetween(lastUpdateDate, systemDate));
		}
		return dates;
	}
	
	public BigDecimal getBaseRateForLoan(Loan loan){
		LoanTypeProfile ltp = loan.getEffectiveLoanTypeProfile();
		RateValue quote = rateValueRepository.findCurrentRateAsOf(ltp.getBaseRate(), loan.getServicingStartDate());
		if(quote != null){
			return quote.getRateValue();
		}
		return BigDecimal.ZERO;
	}
	
	public LoanRateValue setRateForLoan(Loan loan){
		LoanTypeProfile ltp = loan.getEffectiveLoanTypeProfile();
		Rate rate = ltp.getBaseRate();
		List<Date> dates = this.findMissingLoanRateDates(loan);
		LoanRateValue lastRate = null;
		for(Date date:dates){
			RateValue rateValue = rateValueRepository.findRateValue(rate, date);
			LoanRateValue loanRateValue = new LoanRateValue();
			loanRateValue.setLockedDate(date);
			loanRateValue.setMarginValue(BigDecimal.ZERO);
			loanRateValue.setRateValue(rateValue);
			loanRateValue.setLoanID(loan.getLoanID());
			loanRateValue = loanRateValueRepo.save(loanRateValue);
			lastRate = loanRateValue;
		}
		return lastRate;
	}
}
