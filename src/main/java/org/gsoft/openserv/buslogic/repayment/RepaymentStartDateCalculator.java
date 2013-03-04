package org.gsoft.openserv.buslogic.repayment;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Account;
import org.gsoft.openserv.domain.loan.Disbursement;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgramSettings;
import org.gsoft.openserv.domain.loan.RepaymentStartType;
import org.gsoft.openserv.repositories.loan.LoanProgramSettingsRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class RepaymentStartDateCalculator {
	@Resource
	private LoanProgramSettingsRepository loanProgramSettingsRepository;
	@Resource
	private SystemSettingsLogic systemSettingsLogic;

	public Date calculateEarliestRepaymentStartDate(Loan loan){
		Date repaymentStartDate = null;
		if(loan.getDisbursements() != null && loan.getDisbursements().size() > 0){
			LoanProgramSettings settings = loanProgramSettingsRepository.findLoanProgramSettingsByLenderIDAndLoanTypeAndEffectiveDate(loan.getLenderID(), loan.getLoanType(), systemSettingsLogic.getCurrentSystemDate());
			RepaymentStartType repaymentStartType = settings.getRepaymentStartType();
			Disbursement firstDisb = null;
			Disbursement lastDisb = null;
			for(Disbursement disb: loan.getDisbursements()){
				if(firstDisb == null || firstDisb.getDisbursementEffectiveDate().after(disb.getDisbursementEffectiveDate())){
					firstDisb = disb;
				}
				if(lastDisb == null || lastDisb.getDisbursementEffectiveDate().before(disb.getDisbursementEffectiveDate())){
					lastDisb = disb;
				}
			}
			if(repaymentStartType == RepaymentStartType.FIRST_DISBUREMENT){
				repaymentStartDate = new DateTime(firstDisb.getDisbursementEffectiveDate()).plusMonths(settings.getGraceMonths()).toDate();
			}
			else if(repaymentStartType == RepaymentStartType.LAST_DISBURSEMENT){
				repaymentStartDate = new DateTime(lastDisb.getDisbursementEffectiveDate()).plusMonths(settings.getGraceMonths()).toDate();
			}
		}
		return repaymentStartDate;
	}
	
	public Date calculateRepaymentStartDateForAccount(Account account){
		Date repaymentStartDate = null;
		for(Loan loan:account.getLoans()){
			Date loanRepayStart = this.calculateEarliestRepaymentStartDate(loan);
			if(repaymentStartDate == null || (loanRepayStart != null && loanRepayStart.after(repaymentStartDate))){
				repaymentStartDate = loanRepayStart;
			}
		}
		return repaymentStartDate;
	}
}
