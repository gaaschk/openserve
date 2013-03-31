package org.gsoft.openserv.web.loanprogram.model;

import java.io.Serializable;

import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;

public class DefaultLoanProgramSettingsModel implements Serializable{
	private static final long serialVersionUID = 8854407098839115592L;
	private boolean selected;
	private DefaultLoanProgramSettings defaultLoanProgramSettings;
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}

	public DefaultLoanProgramSettings getDefaultLoanProgramSettings() {
		return defaultLoanProgramSettings;
	}

	public void setDefaultLoanProgramSettings(DefaultLoanProgramSettings defaultLoanProgramSettings) {
		this.defaultLoanProgramSettings = defaultLoanProgramSettings;
	}
}
