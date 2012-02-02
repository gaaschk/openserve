package org.gsoft.openserv.buslogic.interest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

@Component
public class CapitalizationLogic {
	public static List<Double> estimateCapEvents(double initialPrincipal, double interestRate, Date capPeriodBeg, Date capPeriodEnd){
		DateTime capPeriodDegDT = new DateTime(capPeriodBeg);
		DateTime capPeriodEndDT = new DateTime(capPeriodEnd);
		double currentPrincipal = initialPrincipal;
		double currentDailyInterestAmount = currentPrincipal*(interestRate/365.25);
		ArrayList<Double> caps = new ArrayList<Double>();
		DateTime previousCap = capPeriodDegDT;
		DateTime lastCap = findNextCapDateBetween(capPeriodDegDT, capPeriodEndDT);
		while(lastCap != null){
			int daysBetween = Days.daysBetween(previousCap, lastCap).getDays();
			double capAmount = currentDailyInterestAmount*daysBetween;
			caps.add(capAmount);
			currentPrincipal += capAmount;
			currentDailyInterestAmount = currentPrincipal*(interestRate/365.25);
			previousCap = lastCap;
			lastCap = findNextCapDateBetween(previousCap, capPeriodEndDT);
		}
		return caps;
	}
	
	private static DateTime findNextCapDateBetween(DateTime fromDate, DateTime toDate){
		DateTime checkDate = fromDate;
		do{
			checkDate = checkDate.plusDays(1);
			if((checkDate.getMonthOfYear() == 3 && checkDate.getDayOfMonth() == 31) ||
					(checkDate.getMonthOfYear() == 6 && checkDate.getDayOfMonth() == 30) ||
					(checkDate.getMonthOfYear() == 9 && checkDate.getDayOfMonth() == 30) ||
					(checkDate.getMonthOfYear() == 12 && checkDate.getDayOfMonth() == 31))
				return checkDate;
		}
		while(!checkDate.isAfter(toDate));
		return null;
	}
}
