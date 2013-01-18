package org.gsoft.openserv.buslogic.amortization;

import java.math.BigDecimal;

import org.gsoft.openserv.util.Constants;
import org.springframework.stereotype.Component;

@Component
public class PaymentAmountCalculator {
	
	public Integer calculatePaymentAmount(Integer principalBalance, BigDecimal annualInterestRate, Integer remainingTerm){
        BigDecimal periodicRate = annualInterestRate.divide(new BigDecimal(Constants.MONTHS_IN_YEAR), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		Integer paymentAmount =
                (int)Math.ceil( ( periodicRate.floatValue() * principalBalance ) /
                        ( 1 - Math.pow( ( 1 + periodicRate.floatValue() ), remainingTerm * ( -1 ) ) ) );
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
}
