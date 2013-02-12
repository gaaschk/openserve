package org.gsoft.openserv.web.models;

import java.io.Serializable;
import java.util.List;

import org.gsoft.openserv.domain.loan.LoanType;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;

public class LoanTypeProfilesModel implements Serializable{
	private static final long serialVersionUID = 8854407098839115592L;
	private LoanType loanType;
	private List<LoanTypeProfile> loanTypeProfiles;
	private boolean selected;
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	public List<LoanTypeProfile> getLoanTypeProfiles() {
		return loanTypeProfiles;
	}
	public void setLoanTypeProfiles(List<LoanTypeProfile> loanTypeProfiles) {
		this.loanTypeProfiles = loanTypeProfiles;
	}
}
