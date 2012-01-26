package org.gsoft.phoenix.buslogic.amortization;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanTypeProfile;
import org.gsoft.phoenix.repositories.loan.LoanTypeProfileRepository;
import org.springframework.stereotype.Component;

@Component
public class RemainingTermCalculator {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	
	public void updateRemainingLoanTerm(Loan loan){
		LoanTypeProfile ltp = loanTypeProfileRepository.findOne(loan.getEffectiveLoanTypeProfileID());
		loan.setRemainingLoanTerm(ltp.getMaximumLoanTerm());
	}
}
