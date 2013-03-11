package org.gsoft.openserv.repositories.loan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.LenderLoanProgramSettings;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgramSettings;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.springframework.stereotype.Repository;

@Repository
public class LoanProgramSettingsRepository {
	@Resource
	private DefaultLoanProgramSettingsRepository loanTypeProfileRepository;
	@Resource
	private LenderLoanProgramSettingsRepository lenderLoanProgramRepository;
	@Resource
	private SystemSettingsLogic systemSettingsLogic;

	public LoanProgramSettings findEffectiveLoanProgramSettingsForLoan(Loan loan){
		return this.findLoanProgramSettingsByLenderIDAndLoanTypeAndEffectiveDate(loan.getLenderID(), loan.getLoanProgram(), systemSettingsLogic.getCurrentSystemDate());
	}

	public LoanProgramSettings findLoanProgramSettingsForLoanEffectiveOn(Loan loan, Date effectiveDate){
		return this.findLoanProgramSettingsByLenderIDAndLoanTypeAndEffectiveDate(loan.getLenderID(), loan.getLoanProgram(), systemSettingsLogic.getCurrentSystemDate());
	}
	
	public List<LoanProgramSettings> findAllLoanProgramSettingsForLoan(Loan loan){
		return this.findAllLoanProgramSettingsByLenderIDAndLoanTypeAndEffectiveDate(loan.getLenderID(), loan.getLoanProgram(), systemSettingsLogic.getCurrentSystemDate());
	}

	public List<LoanProgramSettings> findAllLoanProgramSettingsByLenderIDAndLoanTypeAndEffectiveDate(Long lenderID, LoanProgram loanType, Date effectiveDate) {
		List<DefaultLoanProgramSettings> defaultSettings = loanTypeProfileRepository.findAllDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(loanType, effectiveDate);
		List<LenderLoanProgramSettings> lenderSettings = lenderLoanProgramRepository.findAllByLoanProgramAndEffectiveDate(lenderID, loanType, effectiveDate);
		Stack<DefaultLoanProgramSettings> defaultSettingsStack = new Stack<DefaultLoanProgramSettings>();
		defaultSettingsStack.addAll(defaultSettings);
		Stack<LenderLoanProgramSettings> lenderSettingsStack = new Stack<LenderLoanProgramSettings>();
		lenderSettingsStack.addAll(lenderSettings);
		ArrayList<LoanProgramSettings> loanProgramSettings = new ArrayList<>();
		LoanProgramSettings lastSettings = null;
		while(defaultSettingsStack.size() > 0 && lenderSettingsStack.size() > 0){
			DefaultLoanProgramSettings currentDefaultSettings = defaultSettingsStack.peek();
			LenderLoanProgramSettings currentLenderSettings = lenderSettingsStack.peek();
			LoanProgramSettings lpSettings = new LoanProgramSettings(currentDefaultSettings, currentLenderSettings);
			loanProgramSettings.add(lpSettings);
			if(lastSettings == null){
				if(this.isEndDateBeforeOrEqual(currentDefaultSettings.getEndDate(), currentLenderSettings.getProgramEndDate())){
					defaultSettingsStack.pop();
				}
				if(this.isEndDateBeforeOrEqual(currentLenderSettings.getProgramEndDate(), currentDefaultSettings.getEndDate())){
					lenderSettingsStack.pop();
				}
			} 
			loanProgramSettings.add(lpSettings);
			lastSettings = lpSettings;
		}
		return loanProgramSettings;
	}
	
	private boolean isEndDateBeforeOrEqual(Date one, Date two){
		if(one == two || (one != null && two != null && one.before(two)))
			return true;
		return false;
	}

	public LoanProgramSettings findLoanProgramSettingsByLenderIDAndLoanTypeAndEffectiveDate(Long lenderID, LoanProgram loanType, Date effectiveDate) {
		DefaultLoanProgramSettings defaultSettings = loanTypeProfileRepository.findDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(loanType, effectiveDate);
		LenderLoanProgramSettings lenderSettings = lenderLoanProgramRepository.findByLoanProgramAndEffectiveDate(lenderID, loanType, effectiveDate);
		return new LoanProgramSettings(defaultSettings, lenderSettings);
	}
}
