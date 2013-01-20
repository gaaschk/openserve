package org.gsoft.openserv.buslogic.repayment;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class FirstDueDateCalculator {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	
	public void updateFirstDueDate(Loan loan){
		LoanTypeProfile ltp = loan.getEffectiveLoanTypeProfile();
		loan.setFirstDueDate(new DateTime(loan.getRepaymentStartDate()).plusDays(ltp.getMinDaysToFirstDue()).toDate());
	}
}
