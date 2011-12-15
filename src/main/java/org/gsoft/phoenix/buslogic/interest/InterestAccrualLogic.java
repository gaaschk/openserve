package org.gsoft.phoenix.buslogic.interest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.loan.LoanLookupLogic;
import org.gsoft.phoenix.domain.loan.Loan;
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
		BigDecimal rate = interestFactory.getBaseRateForLoan(loan);
		Days days = Days.daysBetween(new DateTime(fromDate.getTime()), new DateTime(toDate.getTime()));
		BigDecimal actualRate = rate.add(loan.getMargin());
		BigDecimal dailyRate = actualRate.divide(new BigDecimal(365.25),35,RoundingMode.HALF_EVEN);
		Integer principal = loanLookupLogic.getLoanPrincipalBalanceAsOf(loan, fromDate);
		BigDecimal dailyIntAmount = dailyRate.multiply(new BigDecimal(principal));
		return dailyIntAmount.multiply(new BigDecimal(days.getDays()));
	}
}
