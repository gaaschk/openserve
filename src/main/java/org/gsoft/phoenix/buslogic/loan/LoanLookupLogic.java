package org.gsoft.phoenix.buslogic.loan;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanEventRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanLookupLogic {
	@Resource
	private LoanEventRepository loanEventRepository;

	public Integer getLoanPrincipalBalanceAsOf(Loan loan, Date asOfDate){
		return loanEventRepository.findMostRecentLoanEventWithTransactionEffectiveOnOrBeforeDate(loan.getLoanID(), asOfDate).getLoanTransaction().getEndingPrincipal();
	}
}
