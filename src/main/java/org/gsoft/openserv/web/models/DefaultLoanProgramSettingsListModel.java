package org.gsoft.openserv.web.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.domain.rates.Rate;

public class DefaultLoanProgramSettingsListModel implements Serializable{
	private static final long serialVersionUID = 5888431193405855905L;
	private List<DefaultLoanProgramSettingsModel> defaultLoanProgramSettingsModels;
	private List<Rate> allRates;
	
	public List<Rate> getAllRates(){
		return allRates;
	}
	
	public void setAllRates(List<Rate> allRates){
			this.allRates = allRates;
	}
	
	public List<DefaultLoanProgramSettingsModel> getDefaultLoanProgramSettingsModels() {
		if(this.defaultLoanProgramSettingsModels == null){
			this.defaultLoanProgramSettingsModels = new ArrayList<>();
		}
		return defaultLoanProgramSettingsModels;
	}
	
	public void setDefaultLoanProgramSettingsModels(List<DefaultLoanProgramSettingsModel> defaultLoanProgramSettingsModels){
		this.defaultLoanProgramSettingsModels = defaultLoanProgramSettingsModels;
	}
	
	public void addDefaultLoanProgramSettingsList(LoanProgram loanProgram, List<DefaultLoanProgramSettings> defaultLoanProgramSettingsList){
		DefaultLoanProgramSettingsModel model = new DefaultLoanProgramSettingsModel();
		model.setLoanProgram(loanProgram);
		model.setDefaultLoanProgramSettingsList(defaultLoanProgramSettingsList);
		this.getDefaultLoanProgramSettingsModels().add(model);
	}
}
