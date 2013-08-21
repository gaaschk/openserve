package org.gsoft.openserv.web.loanprogram.model;

import java.io.Serializable;
import java.util.List;

public class LoanProgramsModel implements Serializable{
	private static final long serialVersionUID = -8158734666476762683L;

	private List<LoanProgramModel> loanProgramModelList = null;

	public List<LoanProgramModel> getLoanProgramModelList() {
		return loanProgramModelList;
	}

	public void setLoanProgramModelList(List<LoanProgramModel> loanProgramModelList) {
		this.loanProgramModelList = loanProgramModelList;
	}
	
	
}
