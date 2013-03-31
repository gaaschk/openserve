package org.gsoft.openserv.web.loanprogram.model;

import java.io.Serializable;
import java.util.List;

import org.gsoft.openserv.domain.rates.Rate;

public class ManageLoanProgramsModel implements Serializable{
	private static final long serialVersionUID = 5888431193405855905L;
	private List<LoanProgramModel> allLoanProgramModels;
	private LoanProgramModel displayedLoanProgram;
	private List<Rate> allRates;
	
	public List<LoanProgramModel> getAllLoanProgramModels() {
		return allLoanProgramModels;
	}

	public void setAllLoanProgramModels(List<LoanProgramModel> allLoanProgramModels) {
		this.allLoanProgramModels = allLoanProgramModels;
	}

	public LoanProgramModel getDisplayedLoanProgram() {
		return displayedLoanProgram;
	}

	public void setDisplayedLoanProgram(LoanProgramModel displayedLoanProgram) {
		this.displayedLoanProgram = displayedLoanProgram;
	}

	public List<Rate> getAllRates(){
		return allRates;
	}
	
	public void setAllRates(List<Rate> allRates){
			this.allRates = allRates;
	}
}
