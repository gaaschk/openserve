package org.gsoft.openserv.util.time;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.loan.LoanType;
import org.gsoft.openserv.util.jpa.OpenServEnum;
import org.joda.time.DateTime;
import org.joda.time.MonthDay;
import org.joda.time.Period;

public enum FrequencyType implements OpenServEnum<LoanType>{
	MONTHLY(10L, Period.months(1)),
	QUARTERLY(20L, Period.months(3)),
	SEMI_ANNUALLY(30L, Period.months(6)),
	ANNUALLY(40L, Period.months(12));

	private Long frequencyTypeID;
	private Period periodLength = null;
	private List<MonthDay> monthDays = null;
	
	private List<MonthDay> getMonthDays(){
		if(monthDays == null){
			monthDays = new ArrayList<>();
			MonthDay newYears = new MonthDay(1,1);
			MonthDay addedPeriodEnd = newYears;
			do{
				addedPeriodEnd = addedPeriodEnd.plus(periodLength);
				monthDays.add(addedPeriodEnd);
			}while(!newYears.equals(addedPeriodEnd));
		}
		return monthDays;
	}
	
	private FrequencyType(Long id, Period periodLength) {
		this.frequencyTypeID = id;
		this.periodLength = periodLength;
	}
	
	private boolean isPeriodEnd(DateTime dateTime){
		for(MonthDay md:getMonthDays()){
			if(md.getMonthOfYear() == dateTime.getMonthOfYear() && md.getDayOfMonth() == dateTime.getDayOfMonth()){
				return true;
			}
		}
		return false;
	}

	public Date findNextDateAfter(Date theDate){
		DateTime adjustedDate = new DateTime(theDate).plusDays(1);
		while(!this.isPeriodEnd(adjustedDate)){
			adjustedDate = adjustedDate.plusDays(1);
		}
		return adjustedDate.toDate();
	}

	public List<Date> findAllDatesBetween(Date fromDate, Date toDate){
		DateTime endDate = new DateTime(toDate);
		ArrayList<Date> quarterEnds = new ArrayList<Date>();
		DateTime checkDate = new DateTime(this.findNextDateAfter(fromDate));
		while(!checkDate.isAfter(endDate)){
			quarterEnds.add(checkDate.toDate());
			checkDate = checkDate.plus(periodLength);
		}
		return quarterEnds;
	}

	public Long getID() {
		return this.frequencyTypeID;
	}

	public static FrequencyType forID(Long id) {
		for (final FrequencyType frequencyType : values()) {
			if (frequencyType.getID().equals(id)) {
				return frequencyType;
			}
		}
		return null;
	}

}
