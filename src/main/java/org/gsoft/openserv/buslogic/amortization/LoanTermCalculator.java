package org.gsoft.openserv.buslogic.amortization;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.repayment.RepaymentStartDateCalculator;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgramSettings;
import org.gsoft.openserv.repositories.loan.LoanProgramSettingsRepository;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.stereotype.Component;

@Component
public class LoanTermCalculator {
	@Resource
	private LoanProgramSettingsRepository loanProgramSettingsRepository;
	@Resource
	private RepaymentStartDateCalculator repaymentStartDateCalculator;
	
	public int calculateRemainingLoanTermAsOf(Loan loan, Date asOfDate){
		LoanProgramSettings settings = loanProgramSettingsRepository.findLoanProgramSettingsByLenderIDAndLoanProgramAndEffectiveDate(loan.getLenderID(), loan.getLoanProgram(), asOfDate);
		int used = 0;
		Date repaymentStartDate = repaymentStartDateCalculator.calculateEarliestRepaymentStartDate(loan);
		if(repaymentStartDate != null){
			used = Months.monthsBetween(new DateTime(repaymentStartDate),new DateTime(asOfDate)).getMonths(); 
		}
		return settings.getMaximumLoanTerm() - used;
	}
}
