package org.gsoft.openserv.domain.loan.loanstate;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanToLoanStateHistoryConverter implements Converter<Loan, LoanStateHistory> {
	@Resource
	private ApplicationContext applicationContext;
	
	@Override
	public LoanStateHistory convert(Loan loan) {
		LoanStateHistoryBuilder loanStateHistoryBuilder = new LoanStateHistoryBuilder(applicationContext);
		loanStateHistoryBuilder.buildLoanStateHistoryForLoan(loan);
		return loanStateHistoryBuilder.getLoanStateHistory();
	}

}
