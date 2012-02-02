package org.gsoft.openserv.buslogic.amortization;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.springframework.stereotype.Component;

@Component
public class RemainingTermCalculator {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	
	public void updateRemainingLoanTerm(Loan loan){
		LoanTypeProfile ltp = loanTypeProfileRepository.findOne(loan.getEffectiveLoanTypeProfileID());
		loan.setStartingLoanTerm(ltp.getMaximumLoanTerm());
	}
}
