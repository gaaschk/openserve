package org.gsoft.openserv.buslogic.interest;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.gsoft.openserv.repositories.rates.LoanRateValueRepository;
import org.gsoft.openserv.repositories.rates.RateValueRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanInterestRateFactory {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileLogic;
	@Resource
	private RateValueRepository rateValueRepository;
	@Resource
	private LoanRateValueRepository loanRateValueRepo;
	
	public BigDecimal getBaseRateForLoan(Loan loan){
		LoanTypeProfile ltp = loanTypeProfileLogic.findOne(loan.getEffectiveLoanTypeProfileID());
		RateValue quote = rateValueRepository.findCurrentRateAsOf(ltp.getBaseRate(), loan.getServicingStartDate());
		if(quote != null){
			return quote.getRateValue();
		}
		return BigDecimal.ZERO;
	}
	
	public LoanRateValue setRateForLoan(Loan loan){
		RateValue rateValue = rateValueRepository.findRateValueByTickerSymbol("LIBOR.USD1M", loan.getServicingStartDate());
		LoanRateValue loanRateValue = new LoanRateValue();
		loanRateValue.setLockedDate(loan.getServicingStartDate());
		loanRateValue.setMarginValue(BigDecimal.ZERO);
		loanRateValue.setRateValue(rateValue);
		loanRateValue.setLoanID(loan.getLoanID());
		loanRateValue = loanRateValueRepo.save(loanRateValue);
		loan.setInterestRateCurrent(true);
		return loanRateValue;
	}
}
