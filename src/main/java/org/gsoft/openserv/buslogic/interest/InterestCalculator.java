package org.gsoft.openserv.buslogic.interest;

import java.math.BigDecimal;
import java.util.Date;

import org.gsoft.openserv.util.Constants;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class InterestCalculator {

	public static BigDecimal calculateInterest(BigDecimal apr, Integer principal, Date fromDate, Date toDate){
		BigDecimal dailyRate = apr.divide(BigDecimal.valueOf(Constants.DAYS_IN_YEAR), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		int days = Days.daysBetween(new DateTime(fromDate), new DateTime(toDate)).getDays();
		BigDecimal accruedInterest = dailyRate.multiply(BigDecimal.valueOf(principal*days));
		return accruedInterest;
	}
}
