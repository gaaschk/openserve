package org.gsoft.openserv.util.time;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.MonthDay;
import org.joda.time.Period;

public class QuarterlyFrequency implements ChronologicalFrequency{
	private static final MonthDay[] QUARTERS = new MonthDay[]{new MonthDay(3,31),new MonthDay(6,30),new MonthDay(9,30),new MonthDay(12,31)};
	private static final Period PERIOD = Period.months(3);
	
	@Override
	public Date findNextDateAfter(Date theDate) {
		DateTime adjustedDate = new DateTime(theDate);
		while(!this.isQuarterEnd(adjustedDate)){
			adjustedDate = adjustedDate.plusDays(1);
		}
		return adjustedDate.toDate();
	}

	@Override
	public List<Date> findAllDatesBetween(Date fromDate, Date toDate) {
		DateTime endDate = new DateTime(toDate);
		ArrayList<Date> quarterEnds = new ArrayList<Date>();
		DateTime checkDate = new DateTime(this.findNextDateAfter(fromDate));
		while(!checkDate.isAfter(endDate)){
			quarterEnds.add(checkDate.toDate());
			checkDate = checkDate.plus(PERIOD);
		}
		return quarterEnds;
	}
	
	private boolean isQuarterEnd(DateTime dateTime){
		for(MonthDay md:QUARTERS){
			if(md.getMonthOfYear() == dateTime.getMonthOfYear() && md.getDayOfMonth() == dateTime.getDayOfMonth()){
				return true;
			}
		}
		return false;
	}

}
