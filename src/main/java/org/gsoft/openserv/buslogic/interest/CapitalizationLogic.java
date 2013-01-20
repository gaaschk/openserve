package org.gsoft.openserv.buslogic.interest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gsoft.openserv.util.Constants;
import org.gsoft.openserv.util.time.FrequencyType;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

@Component
public class CapitalizationLogic {
	private FrequencyType frequency = FrequencyType.QUARTERLY;
	
	public List<Double> estimateCapEvents(double initialPrincipal, double interestRate, Date capPeriodBeg, Date capPeriodEnd){
		double currentPrincipal = initialPrincipal;
		double currentDailyInterestAmount = currentPrincipal*(interestRate/Constants.DAYS_IN_YEAR);
		ArrayList<Double> caps = new ArrayList<Double>();
		List<Date> capDates = frequency.findAllDatesBetween(capPeriodBeg, capPeriodEnd);
		DateTime fromDate = new DateTime(capPeriodBeg);
		for(Date capDate:capDates){
			int daysBetween = Days.daysBetween(fromDate, new DateTime(capDate)).getDays();
			double capAmount = currentDailyInterestAmount*daysBetween;
			caps.add(capAmount);
			currentPrincipal += capAmount;
			currentDailyInterestAmount = currentPrincipal*(interestRate/Constants.DAYS_IN_YEAR);
		}
		return caps;
	}

}
