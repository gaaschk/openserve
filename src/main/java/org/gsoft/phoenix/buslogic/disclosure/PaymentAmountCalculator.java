package org.gsoft.phoenix.buslogic.disclosure;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class PaymentAmountCalculator {
	private static final int MONTHS_IN_YEAR = 12;
	
	public Integer calculatePaymentAmount(Integer principalBalance, BigDecimal annualInterestRate, Integer remainingTerm){
        BigDecimal periodicRate = annualInterestRate.divide(new BigDecimal(MONTHS_IN_YEAR));
		Integer paymentAmount =
                (int)Math.ceil( ( periodicRate.floatValue() * principalBalance ) /
                        ( 1 - Math.pow( ( 1 + periodicRate.floatValue() ), remainingTerm * ( -1 ) ) ) );
		return paymentAmount;
	}
}
