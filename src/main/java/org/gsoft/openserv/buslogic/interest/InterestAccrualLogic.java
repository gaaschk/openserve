package org.gsoft.openserv.buslogic.interest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.loan.LoanLookupLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.util.Constants;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

@Component
public class InterestAccrualLogic {
	
	@Resource
	private LoanInterestRateFactory interestFactory;
	@Resource
	private LoanLookupLogic loanLookupLogic;
	
	public BigDecimal calculateLoanInterestAmountForPeriod(Loan loan, Date fromDate, Date toDate){
		Days days = Days.daysBetween(new DateTime(fromDate.getTime()), new DateTime(toDate.getTime()));
		BigDecimal actualRate = this.getInterestRateForLoan(loan);
		BigDecimal dailyRate = actualRate.divide(new BigDecimal(Constants.DAYS_IN_YEAR),Constants.INTEREST_ROUNDING_SCALE_35,RoundingMode.HALF_EVEN);
		Integer principal = loanLookupLogic.getLoanPrincipalBalanceAsOf(loan, fromDate);
		BigDecimal dailyIntAmount = dailyRate.multiply(new BigDecimal(principal));
		return dailyIntAmount.multiply(new BigDecimal(days.getDays()));
	}
	
	public BigDecimal getInterestRateForLoan(Loan loan){
		BigDecimal rate = interestFactory.getBaseRateForLoan(loan);
		return rate.add(loan.getMargin());
	}
}
