package org.gsoft.openserv.buslogic.repayment;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgramSettings;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.repositories.loan.LoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class FirstDueDateCalculator {
	@Resource
	private LoanProgramSettingsRepository loanProgramSettingsRepository;
	@Resource
	private RepaymentStartDateCalculator repaymentStartDateCalculator;
	
	public void updateFirstDueDate(Loan loan){
		LoanProgramSettings settings = loanProgramSettingsRepository.findEffectiveLoanProgramSettingsForLoan(loan);
		loan.setFirstDueDate(new DateTime(repaymentStartDateCalculator.calculateEarliestRepaymentStartDate(loan)).plusDays(settings.getMinDaysToFirstDue()).toDate());
	}
}
