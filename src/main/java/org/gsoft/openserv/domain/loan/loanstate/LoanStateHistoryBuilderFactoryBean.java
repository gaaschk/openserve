package org.gsoft.openserv.domain.loan.loanstate;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LoanStateHistoryBuilderFactoryBean{
	@Resource
	private ApplicationContext applicationContext;
	
	LoanStateHistoryBuilderFactoryBean() {
	}
	
	public LoanStateHistoryBuilder createBuilder(){
		return new LoanStateHistoryBuilder(applicationContext);
	}

	public LoanStateHistoryBuilder createBuilder(LoanStateHistory beginningLoanStateHistory){
		LoanStateHistoryBuilder builder = new LoanStateHistoryBuilder(applicationContext);
		builder.setLoanStateHistory(beginningLoanStateHistory);
		return builder;
	}
}
