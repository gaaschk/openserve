package org.gsoft.phoenix.buslogic.interest;

import java.math.BigDecimal;

import org.gsoft.phoenix.domain.loan.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanInterestRateFactory {
	public BigDecimal getBaseRateForLoan(Loan loan){
		return new BigDecimal(0.0425);
	}
}
