package org.gsoft.openserv.web.loanprogram.model;

import java.io.Serializable;
import java.util.List;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;

public class DefaultLoanProgramSettingsModel implements Serializable{
	private static final long serialVersionUID = 8854407098839115592L;
	private LoanProgram loanProgram;
	private List<DefaultLoanProgramSettings> loanTypeProfiles;
	private boolean selected;
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public LoanProgram getLoanProgram() {
		return loanProgram;
	}
	public void setLoanProgram(LoanProgram loanType) {
		this.loanProgram = loanType;
	}
	public List<DefaultLoanProgramSettings> getLoanTypeProfiles() {
		return loanTypeProfiles;
	}
	public void setDefaultLoanProgramSettingsList(List<DefaultLoanProgramSettings> loanTypeProfiles) {
		this.loanTypeProfiles = loanTypeProfiles;
	}
}
