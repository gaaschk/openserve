package org.gsoft.openserv.repositories.loan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.LenderLoanProgram;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgramSettings;
import org.gsoft.openserv.domain.loan.LoanType;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.springframework.stereotype.Repository;

@Repository
public class LoanProgramSettingsRepository {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	@Resource
	private LenderLoanProgramRepository lenderLoanProgramRepository;
	@Resource
	private SystemSettingsLogic systemSettingsLogic;

	public LoanProgramSettings findEffectiveLoanProgramSettingsForLoan(Loan loan){
		return this.findLoanProgramSettingsByLenderIDAndLoanTypeAndEffectiveDate(loan.getLenderID(), loan.getLoanType(), systemSettingsLogic.getCurrentSystemDate());
	}

	public List<LoanProgramSettings> findAllLoanProgramSettingsForLoan(Loan loan){
		return this.findAllLoanProgramSettingsByLenderIDAndLoanTypeAndEffectiveDate(loan.getLenderID(), loan.getLoanType(), systemSettingsLogic.getCurrentSystemDate());
	}

	public List<LoanProgramSettings> findAllLoanProgramSettingsByLenderIDAndLoanTypeAndEffectiveDate(Long lenderID, LoanType loanType, Date effectiveDate) {
		List<LoanTypeProfile> defaultSettings = loanTypeProfileRepository.findLoanTypeProfilesByLoanTypeAndEffectiveDate(loanType, effectiveDate);
		List<LenderLoanProgram> lenderSettings = lenderLoanProgramRepository.findAllByLoanTypeAndEffectiveDate(lenderID, loanType, effectiveDate);
		Stack<LoanTypeProfile> defaultSettingsStack = new Stack<LoanTypeProfile>();
		defaultSettingsStack.addAll(defaultSettings);
		Stack<LenderLoanProgram> lenderSettingsStack = new Stack<LenderLoanProgram>();
		lenderSettingsStack.addAll(lenderSettings);
		ArrayList<LoanProgramSettings> loanProgramSettings = new ArrayList<>();
		LoanProgramSettings lastSettings = null;
		while(defaultSettingsStack.size() > 0 && lenderSettingsStack.size() > 0){
			LoanTypeProfile currentDefaultSettings = defaultSettingsStack.peek();
			LenderLoanProgram currentLenderSettings = lenderSettingsStack.peek();
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

	public LoanProgramSettings findLoanProgramSettingsByLenderIDAndLoanTypeAndEffectiveDate(Long lenderID, LoanType loanType, Date effectiveDate) {
		LoanTypeProfile defaultSettings = loanTypeProfileRepository.findLoanTypeProfileByLoanTypeAndEffectiveDate(loanType, effectiveDate);
		LenderLoanProgram lenderSettings = lenderLoanProgramRepository.findByLoanTypeAndEffectiveDate(lenderID, loanType, effectiveDate);
		return new LoanProgramSettings(defaultSettings, lenderSettings);
	}
}
