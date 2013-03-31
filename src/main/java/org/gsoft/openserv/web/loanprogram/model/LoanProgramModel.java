package org.gsoft.openserv.web.loanprogram.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gsoft.openserv.domain.loan.LoanProgram;

public class LoanProgramModel implements Serializable{
	private static final long serialVersionUID = 2474967440082856473L;

	private boolean isSelected;
	private LoanProgram loanProgram;
	private List<DefaultLoanProgramSettingsModel> allDefaultLoanProgramSettingsModels;
	private DefaultLoanProgramSettingsModel displayedDefaultLoanProgramSettingsModel;
	
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public LoanProgram getLoanProgram() {
		return loanProgram;
	}
	public void setLoanProgram(LoanProgram loanProgram) {
		this.loanProgram = loanProgram;
	}
	public List<DefaultLoanProgramSettingsModel> getAllDefaultLoanProgramSettingsModels() {
		if(this.allDefaultLoanProgramSettingsModels == null){
			this.allDefaultLoanProgramSettingsModels = new ArrayList<>();
		}
		return allDefaultLoanProgramSettingsModels;
	}
	
	public void setAllDefaultLoanProgramSettingsModels(List<DefaultLoanProgramSettingsModel> defaultLoanProgramSettingsModels){
		this.allDefaultLoanProgramSettingsModels = defaultLoanProgramSettingsModels;
	}
	
	public DefaultLoanProgramSettingsModel getDisplayedDefaultLoanProgramSettingsModel() {
		return displayedDefaultLoanProgramSettingsModel;
	}

	public void setDisplayedDefaultLoanProgramSettingsModel(
			DefaultLoanProgramSettingsModel displayedDefaultLoanProgramSettingsModel) {
		this.displayedDefaultLoanProgramSettingsModel = displayedDefaultLoanProgramSettingsModel;
	}
}
