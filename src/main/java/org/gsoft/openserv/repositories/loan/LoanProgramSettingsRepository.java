package org.gsoft.openserv.repositories.loan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.domain.loan.LenderLoanProgramSettings;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.domain.loan.LoanProgramSettings;
import org.springframework.stereotype.Repository;

@Repository
public class LoanProgramSettingsRepository {
	@Resource
	private DefaultLoanProgramSettingsRepository defaultLoanProgramSettingsRepository;
	@Resource
	private LenderLoanProgramSettingsRepository lenderLoanProgramRepository;
	@Resource
	private SystemSettingsLogic systemSettingsLogic;

	public LoanProgramSettings findEffectiveLoanProgramSettingsForLoan(Loan loan){
		return this.findLoanProgramSettingsByLenderIDAndLoanProgramAndEffectiveDate(loan.getLenderID(), loan.getLoanProgram(), systemSettingsLogic.getCurrentSystemDate());
	}

	public LoanProgramSettings findLoanProgramSettingsForLoanEffectiveOn(Loan loan, Date effectiveDate){
		return this.findLoanProgramSettingsByLenderIDAndLoanProgramAndEffectiveDate(loan.getLenderID(), loan.getLoanProgram(), systemSettingsLogic.getCurrentSystemDate());
	}
	
	public List<LoanProgramSettings> findAllLoanProgramSettingsForLoan(Loan loan){
		return this.findAllLoanProgramSettingsByLenderIDAndLoanProgramAndEffectiveDate(loan.getLenderID(), loan.getLoanProgram(), systemSettingsLogic.getCurrentSystemDate());
	}

	public List<LoanProgramSettings> findAllLoanProgramSettingsByLenderIDAndLoanProgramAndEffectiveDate(Long lenderID, LoanProgram loanProgram, Date effectiveDate) {
		List<DefaultLoanProgramSettings> defaultSettings = defaultLoanProgramSettingsRepository.findAllDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(loanProgram, effectiveDate);
		List<LenderLoanProgramSettings> lenderSettings = lenderLoanProgramRepository.findAllByLoanProgramAndEffectiveDate(lenderID, loanProgram, effectiveDate);
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

	public LoanProgramSettings findLoanProgramSettingsByLenderIDAndLoanProgramAndEffectiveDate(Long lenderID, LoanProgram loanProgram, Date effectiveDate) {
		DefaultLoanProgramSettings defaultSettings = defaultLoanProgramSettingsRepository.findDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(loanProgram, effectiveDate);
		LenderLoanProgramSettings lenderSettings = lenderLoanProgramRepository.findByLoanProgramAndEffectiveDate(lenderID, loanProgram, effectiveDate);
		return new LoanProgramSettings(defaultSettings, lenderSettings);
	}
}
