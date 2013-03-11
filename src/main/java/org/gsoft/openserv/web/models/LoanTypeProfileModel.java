package org.gsoft.openserv.web.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.domain.rates.Rate;

public class LoanTypeProfileModel implements Serializable{
	private static final long serialVersionUID = 5888431193405855905L;
	private List<LoanTypeProfilesModel> loanTypeProfilesModels;
	private List<Rate> allRates;
	
	public List<Rate> getAllRates(){
		return allRates;
	}
	
	public void setAllRates(List<Rate> allRates){
			this.allRates = allRates;
	}
	
	public List<LoanTypeProfilesModel> getLoanTypeProfilesModels() {
		if(this.loanTypeProfilesModels == null){
			this.loanTypeProfilesModels = new ArrayList<>();
		}
		return loanTypeProfilesModels;
	}
	
	public void setLoanTypeProfilesModels(List<LoanTypeProfilesModel> loanTypeProfilesModels){
		this.loanTypeProfilesModels = loanTypeProfilesModels;
	}
	
	public void addLoanTypeProfiles(LoanProgram loanType, List<DefaultLoanProgramSettings> loanTypeProfiles){
		LoanTypeProfilesModel model = new LoanTypeProfilesModel();
		model.setLoanType(loanType);
		model.setLoanTypeProfiles(loanTypeProfiles);
		this.getLoanTypeProfilesModels().add(model);
	}
}
