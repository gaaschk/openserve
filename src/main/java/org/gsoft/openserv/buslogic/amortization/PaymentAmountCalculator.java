package org.gsoft.openserv.buslogic.amortization;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.amortization.AmortizationLoanPayment;
import org.gsoft.openserv.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.util.Constants;

public class PaymentAmountCalculator {
	
	public static Integer calculatePaymentAmount(Integer principalBalance, BigDecimal annualInterestRate, Integer remainingTerm){
        BigDecimal periodicRate = annualInterestRate.divide(new BigDecimal(Constants.MONTHS_IN_YEAR), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		Integer paymentAmount = periodicRate.multiply(BigDecimal.valueOf(principalBalance)).divide(BigDecimal.ONE.subtract(periodicRate.add(BigDecimal.valueOf(1)).pow(remainingTerm * ( -1 ), MathContext.DECIMAL128)), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE).intValue();
		if(paymentAmount <= 0){
			if(remainingTerm > 0){
				paymentAmount = principalBalance/remainingTerm;
			}
			else{
				paymentAmount = principalBalance;
			}
		}
		return paymentAmount;
	}
	
	public static BigDecimal calculatePaymentAmountExact(Integer principalBalance, BigDecimal annualInterestRate, Integer remainingTerm){
        BigDecimal periodicRate = annualInterestRate.divide(new BigDecimal(Constants.MONTHS_IN_YEAR), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		BigDecimal paymentAmount = periodicRate.multiply(BigDecimal.valueOf(principalBalance)).divide(BigDecimal.ONE.subtract(periodicRate.add(BigDecimal.valueOf(1)).pow(remainingTerm * ( -1 ), MathContext.DECIMAL128)), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		return paymentAmount;
	}
}
