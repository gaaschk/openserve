package org.gsoft.phoenix.buslogic.loan;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanTypeProfile;
import org.gsoft.phoenix.repositories.loan.LoanTypeProfileRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanTypeLogic {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	
	public LoanTypeProfile getLoanTypeProfileForLoan(Loan loan){
		return loanTypeProfileRepository.findLoanTypeProfileByLoanTypeAndEffectiveDate(loan.getLoanType(), new Date());
	}
}
