package org.gsoft.phoenix.buslogic.interest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;
import org.springframework.stereotype.Component;

@Component
public class InternalRateOfReturnCalculator {
	public enum OIDEventType{
		DISBURSEMENT,
		PAYMENT,
		INTEREST_CAP,
		INTEREST_RATE_CHANGE,
		OFEE_CHARGED
	}

	public double calculateReturnRate(double presentValue, double futureValue, int periodsToMaturity){
		return Math.pow(futureValue/presentValue,1.0/periodsToMaturity) - 1.0;
	}
	
	public double calculateYTMIteration(double previousYTMEst, double presentValue, double paymentAmount, double futureValue, int periods){
		return (paymentAmount + futureValue)*Math.pow(previousYTMEst,periods+1) - futureValue*Math.pow(previousYTMEst,periods) - (paymentAmount+presentValue)*previousYTMEst + presentValue;
	}

	public double calculateDerivativeYTMIteration(double previousYTMEst, double presentValue, double paymentAmount, double futureValue, int periods){
		return (periods+1)*(paymentAmount + futureValue)*Math.pow(previousYTMEst,periods) - periods*futureValue*Math.pow(previousYTMEst,periods - 1) - (paymentAmount+presentValue);
	}

	public double calculateBondYTM(double originalPrice, double paymentRate, double futureValue, int periods){
		double previousYTMEst = paymentRate;
		double paymentAmount = paymentRate*futureValue;
		double errorBoundary = .00001;

		if (paymentRate == 0){
			return calculateReturnRate(originalPrice,futureValue,periods);
		}
			
		for (int i = 0; i < 100; i++){
			if (Math.abs(calculateYTMIteration(previousYTMEst,originalPrice,paymentAmount,futureValue,periods)) < errorBoundary) break;
			while (Math.abs(calculateDerivativeYTMIteration(previousYTMEst,originalPrice,paymentAmount,futureValue,periods)) < errorBoundary) previousYTMEst+= .1;
			previousYTMEst = previousYTMEst - (calculateYTMIteration(previousYTMEst,originalPrice,paymentAmount,futureValue,periods)/calculateDerivativeYTMIteration(previousYTMEst,originalPrice,paymentAmount,futureValue,periods));
		}
		if (Math.abs(calculateYTMIteration(previousYTMEst,originalPrice,paymentAmount,futureValue,periods)) >= errorBoundary) return -1;  // error

		return (1/previousYTMEst) - 1;
	}
	
	private int countDaysForPeriod(Date periodBegin, Date periodEnd){
		DateTime perBegDT = new DateTime(periodBegin);
		DateTime perEndDT = new DateTime(periodEnd);
		return Days.daysBetween(perBegDT, perEndDT).getDays();
	}
	
	private double calculateInterestAmountForPeriod(double principal, double rate, Date periodBegin, Date periodEnd){
		int days = this.countDaysForPeriod(periodBegin, periodEnd);
		return principal*(rate/365.25)*days;
	}
	
	public double calculateOIDForPeriod(double adjustedIssuePrice, double futureValue, Date periodBegin, Date periodEnd, double expectedPaymentAmount, double interestRate){
		double remainingBalance = futureValue-adjustedIssuePrice;
		int remainingTerm = (int)Math.ceil((Math.log(expectedPaymentAmount/remainingBalance)-Math.log((expectedPaymentAmount/remainingBalance)-(interestRate/12)))/Math.log(1+(interestRate/12)));
		String line = "";
		line += this.padWithSpaces("Remaining term: " + remainingTerm + ", ", 21);
		double ytmRate = this.calculateBondYTM(adjustedIssuePrice, 0, futureValue, remainingTerm);
		line += this.padWithSpaces("YTM rate: " + ytmRate + ", ", 33);
		System.out.print(line);
		return adjustedIssuePrice*ytmRate;
	}
	
	public double calculateOIDUsingDecliningBalanceMethod(DateTime beginDate, DateTime endDate, DateTime maturityDate, double aip, double matureValue){
		int daysToMaturity = Days.daysBetween(beginDate, maturityDate).getDays();
		int daysInPeriod = Days.daysBetween(beginDate, endDate).getDays();
		double dailyRate = (1d/(double)daysToMaturity)*2d;
		double accumulatedOID = 0;
		double currentCost = aip;
		for(int day=0;day<daysInPeriod;day++){
			double oidExpense = currentCost*dailyRate;
			accumulatedOID += oidExpense;
			currentCost -= oidExpense;
		}
		
		return accumulatedOID;
	}
	
	public void runOIDCalculation(List<OIDEvent> oidEvents){
		Collections.sort(oidEvents, new Comparator<OIDEvent>(){
			@Override
			public int compare(OIDEvent arg0, OIDEvent arg1) {
				return arg0.getEventDate().compareTo(arg1.getEventDate());
			}
			
		});
		assert(oidEvents.get(0).oidEventType == OIDEventType.DISBURSEMENT);
		double adjustedIssuePrice = 0;
		DateTime lastPaymentDate = new DateTime();
		double totalOID = 0;
		double currentInterestRate = 0;
		double maturityValue = 0;
		double accruedUnpaidOID = 0;
		double paidOID = 0;
		for(OIDEvent event:oidEvents){
			switch(event.oidEventType){
				case DISBURSEMENT: 
					adjustedIssuePrice += event.eventValue;
					maturityValue += event.eventValue;
					lastPaymentDate = new DateTime(event.eventDate);
					break;
				case OFEE_CHARGED: 
					maturityValue += event.eventValue;
					totalOID += event.eventValue;
					break;
				case INTEREST_CAP: 
					maturityValue += event.eventValue;
					totalOID += event.eventValue;
					break;
				case INTEREST_RATE_CHANGE: 
					currentInterestRate = event.eventValue;
					break;
				case PAYMENT:
					String line = this.padWithSpaces("Previous Adjusted Issue Price: " + adjustedIssuePrice + ", ", 53);
					line += this.padWithSpaces("Previous Future Value: " + maturityValue + ", ", 43);
					System.out.print(line);
					line = "";
					double currentOID = this.calculateOIDForPeriod(adjustedIssuePrice, maturityValue, lastPaymentDate.toDate(), event.eventDate, event.expectedPaymentAmount, currentInterestRate);
					line += this.padWithSpaces("Last Payment Date: " + lastPaymentDate.toDate() + ", ", 49);
					line += this.padWithSpaces("Current Payment Date (Period End): " + event.eventDate + ", ", 65);
					line += this.padWithSpaces("Required Payment Amount: " + maturityValue + ", ", 45);
					line += this.padWithSpaces("Accrued OID: " + currentOID + ", ", 33);
					line += this.padWithSpaces("Payment Amount: " + event.eventValue + ", ", 24);
					line += this.padWithSpaces("Total OID: " + totalOID + ", ", 18);
					line += this.padWithSpaces("Accrued OID: " + (accruedUnpaidOID + paidOID) + ", ", 33);
					line += this.padWithSpaces("Paid OID: " + paidOID + ", ", 30);

					lastPaymentDate = new DateTime(event.eventDate);
					adjustedIssuePrice += event.eventValue;
					//maturityValue -= event.eventValue;
					accruedUnpaidOID += currentOID;
					adjustedIssuePrice += currentOID;
					double oidToPay = (event.eventValue > accruedUnpaidOID)?accruedUnpaidOID:event.eventValue;
					
					accruedUnpaidOID -= oidToPay;
					paidOID += oidToPay;

					line += this.padWithSpaces("New Adjusted Issue Price: " + adjustedIssuePrice + ", ", 47);
					line += this.padWithSpaces("New Future Value: " + maturityValue, 55);
					System.out.println(line);
					break;
			}
		}
	}
	
	private String padWithSpaces(String toPad, int count){
		for(int i=toPad.length();i<count;i++)toPad+=" ";
		return toPad;
	}
	
	private double compoundOIDAtRateForDateRange(DateTime begin, DateTime end, double rate, double aip){
		int periods = Months.monthsBetween(begin, end).getMonths();
		for(int i=0; i<periods; i++){
			aip = (aip*rate)+aip;
		}
		return aip;
	}

	private double compoundAtAPRForDateRange(DateTime begin, DateTime end, double rate, double aip){
		int years = Years.yearsBetween(begin, end).getYears();
		int days = Days.daysBetween(begin.plusYears(years), end).getDays();
		double accumulatedAmount = 0;
		while(years > 0){
			double accruedAmount = aip*rate;
			accumulatedAmount += accruedAmount;
			aip += accruedAmount;
			years--;
		}
		double accruedAmount = aip*(rate/365.25)*days;
		accumulatedAmount += accruedAmount;
		return accumulatedAmount;
	}

	public static void main(String ... args){
		DateTime carrletonBegin = new DateTime(2006, 12, 21, 0,0,0);
		DateTime carrletonEnd = new DateTime(2008, 7, 15, 0,0,0);
		DateTime carrletonMaturity = new DateTime(2020,7,5,0,0,0);
		
		InternalRateOfReturnCalculator calculator = new InternalRateOfReturnCalculator();
		double oidAmount = calculator.compoundAtAPRForDateRange(carrletonBegin, carrletonEnd, .057647, 8750);
		oidAmount += (oidAmount*(.057647/365.25)*25);
		System.out.println("OID:" + oidAmount);
		/*
		double carrletonYTMRate = calculator.calculateBondYTM(7312.59, 0, 7409.59, 180);
		System.out.println("Carrleton YTM Rate: " + carrletonYTMRate);
		int periods = Months.monthsBetween(carrletonBegin, carrletonEnd).getMonths();
		
		System.out.println("Carrleton First OID Amount: " + 7312.59*carrletonYTMRate);
		
		ArrayList<OIDEvent> events = new ArrayList<OIDEvent>();
		events.add(calculator.new OIDEvent(OIDEventType.DISBURSEMENT, new DateTime(2003,9,1,0,0,0).toDate(), 4753,0));
		events.add(calculator.new OIDEvent(OIDEventType.OFEE_CHARGED, new DateTime(2003,9,1,0,0,0).toDate(), 97,0));
		events.add(calculator.new OIDEvent(OIDEventType.INTEREST_RATE_CHANGE, new DateTime(2011,9,1,0,0,0).toDate(),.05,0));
		events.add(calculator.new OIDEvent(OIDEventType.PAYMENT, new DateTime(2011,10,5,0,0,0).toDate(), 15000, 123));
		events.add(calculator.new OIDEvent(OIDEventType.PAYMENT, new DateTime(2011,11,5,0,0,0).toDate(), 123, 123));
		//just need to ensure that all of the events are in the correct order before we process them.
		Collections.sort(events, new Comparator<OIDEvent>(){
			@Override
			public int compare(OIDEvent o1, OIDEvent o2) {
				return o1.eventDate.compareTo(o2.eventDate);
			}
		});
		calculator.runOIDCalculation(events);
		*/
	}
	
	class OIDEvent{
		private OIDEventType oidEventType;
		private Date eventDate;
		private double eventValue;
		private double expectedPaymentAmount;
		
		public OIDEvent(OIDEventType oidEventType, Date eventDate, double eventAmount, double expectedPaymentAmount) {
			super();
			this.oidEventType = oidEventType;
			this.eventDate = eventDate;
			this.eventValue = eventAmount;
			this.expectedPaymentAmount = expectedPaymentAmount;
		}
		
		public OIDEventType getOidEventType() {
			return oidEventType;
		}
		public void setOidEventType(OIDEventType oidEventType) {
			this.oidEventType = oidEventType;
		}
		public Date getEventDate() {
			return eventDate;
		}
		public void setEventDate(Date eventDate) {
			this.eventDate = eventDate;
		}
		public double getEventValue() {
			return eventValue;
		}
		public void setEventValue(double eventValue) {
			this.eventValue = eventValue;
		}

		public double getExpectedPaymentAmount() {
			return expectedPaymentAmount;
		}

		public void setExpectedPaymentAmount(double expectedPaymentAmount) {
			this.expectedPaymentAmount = expectedPaymentAmount;
		}
	}
}
