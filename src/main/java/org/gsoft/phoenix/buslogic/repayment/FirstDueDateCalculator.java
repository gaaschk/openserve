package org.gsoft.phoenix.buslogic.repayment;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanTypeProfile;
import org.gsoft.phoenix.repositories.loan.LoanTypeProfileRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class FirstDueDateCalculator {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	
	public void updateFirstDueDate(Loan loan){
		Long ltpID = loan.getEffectiveLoanTypeProfileID();
		LoanTypeProfile ltp = loanTypeProfileRepository.findOne(ltpID);
		loan.setFirstDueDate(new DateTime(loan.getRepaymentStartDate()).plusDays(ltp.getMinDaysToFirstDue()).toDate());
	}
}
