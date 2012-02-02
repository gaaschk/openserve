package org.gsoft.openserv.buslogic.loan;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanTypeLogic {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	
	public LoanTypeProfile updateLoanTypeProfileForLoan(Loan loan){
		LoanTypeProfile ltp = loanTypeProfileRepository.findLoanTypeProfileByLoanTypeAndEffectiveDate(loan.getLoanType(), new Date());
		loan.setEffectiveLoanTypeProfileID(ltp.getID());
		return ltp;
	}
}
