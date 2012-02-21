package org.gsoft.openserv.buslogic.interest;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.rates.DailyRateQuote;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.gsoft.openserv.repositories.rates.DailyRateQuoteRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanInterestRateFactory {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileLogic;
	@Resource
	private DailyRateQuoteRepository dailyRateQuoteRepository;
	
	public BigDecimal getBaseRateForLoan(Loan loan){
		LoanTypeProfile ltp = loanTypeProfileLogic.findOne(loan.getEffectiveLoanTypeProfileID());
		DailyRateQuote quote = dailyRateQuoteRepository.findCurrentRateAsOf(ltp.getBaseRate(), loan.getServicingStartDate());
		if(quote != null)
			return quote.getValue();
		return BigDecimal.ZERO;
	}
}
