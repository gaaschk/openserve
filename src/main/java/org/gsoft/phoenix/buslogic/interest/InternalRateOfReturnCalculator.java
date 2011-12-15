package org.gsoft.phoenix.buslogic.interest;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

public class InternalRateOfReturnCalculator {
	public static double calculateReturnRate(double presentValue, double futureValue, int periodsToMaturity){
		return Math.pow(futureValue/presentValue,1.0/periodsToMaturity) - 1.0;
	}
	
	public static double calculateYTMIteration(double previousYTMEst, double presentValue, double paymentAmount, double futureValue, int periods){
		return (paymentAmount + futureValue)*Math.pow(previousYTMEst,periods+1) - futureValue*Math.pow(previousYTMEst,periods) - (paymentAmount+presentValue)*previousYTMEst + presentValue;
	}

	public static double calculateDerivativeYTMIteration(double previousYTMEst, double presentValue, double paymentAmount, double futureValue, int periods){
		return (periods+1)*(paymentAmount + futureValue)*Math.pow(previousYTMEst,periods) - periods*futureValue*Math.pow(previousYTMEst,periods - 1) - (paymentAmount+presentValue);
	}

	public static double calculateBondYTM(double originalPrice, double paymentRate, double futureValue, int periods){
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
	
	public static void main(String ... args){
		double disbursedAmount = 15000;
		double originationFee = 1800;
		double interestRate = .05;
		DateTime capStart = new DateTime(2011, 1, 1, 0,0,0);
		DateTime gradSep = new DateTime(2011, 1, 1, 0,0,0);
		gradSep = gradSep.plusYears(0);
		List<Double> capAmounts = CapitalizationLogic.estimateCapEvents(disbursedAmount, interestRate, capStart.toDate(), gradSep.toDate());
		int loanTerm = 180;
		double issuePrice = disbursedAmount - originationFee;
		double couponRate = 0;
		double futureValue = disbursedAmount;
		int periods = loanTerm/6;
		DateTime maturityDate = capStart.plusMonths(loanTerm);
		
		System.out.println("Loan Amount: " + disbursedAmount);
		System.out.println("Orig. Fee: " + originationFee);
		System.out.print("Cap Amounts between " + capStart + " and " + gradSep + ": {");
		String comma = "";
		for(double capAmount:capAmounts){
			System.out.print(comma + capAmount);
			futureValue +=capAmount;
			comma = ", ";
		}
		System.out.println("}");
		System.out.println("Issue Price: " + issuePrice);
		System.out.println("Future Value: " + futureValue);
		System.out.println("Maturity Date: " + maturityDate);
		double YTMRate = InternalRateOfReturnCalculator.calculateBondYTM(issuePrice, couponRate, futureValue, periods);
		System.out.println("YTM Rate: " + YTMRate);
		double adjustedIssuePrice = issuePrice;
		double periodEarnedOID = 0;
		double totalEarnedOID = 0;
		DateTime oidPeriodDate = capStart.plusMonths(6);
		while(!oidPeriodDate.isAfter(maturityDate)){
			periodEarnedOID = adjustedIssuePrice*YTMRate;
			System.out.println("OID for period:" + oidPeriodDate);
			System.out.println("Adjusted Issue Price: " + adjustedIssuePrice + " * YTM Rate = Earned OID: " + periodEarnedOID);
			totalEarnedOID += periodEarnedOID;
			adjustedIssuePrice += periodEarnedOID;
			oidPeriodDate = oidPeriodDate.plusMonths(6);
		}
		System.out.println("Total Earned OID: " + totalEarnedOID);
	}
}
