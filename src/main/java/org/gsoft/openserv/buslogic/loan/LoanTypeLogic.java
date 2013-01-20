package org.gsoft.openserv.buslogic.loan;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanTypeLogic {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	@Resource
	private SystemSettingsLogic systemSettingsLogic;
	
	public LoanTypeProfile updateLoanTypeProfileForLoan(Loan loan){
		LoanTypeProfile ltp = loanTypeProfileRepository.findLoanTypeProfileByLoanTypeAndEffectiveDate(loan.getLoanType(), systemSettingsLogic.getCurrentSystemDate());
		loan.setEffectiveLoanTypeProfile(ltp);
		return ltp;
	}
}
