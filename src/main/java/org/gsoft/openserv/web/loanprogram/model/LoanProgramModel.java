package org.gsoft.openserv.web.loanprogram.model;

import java.io.Serializable;

public class LoanProgramModel implements Serializable{
	private static final long serialVersionUID = 2474967440082856473L;

	private Long loanProgramID;
	private String name;
	private String description;

	public Long getLoanProgramID() {
		return loanProgramID;
	}

	public void setLoanProgramID(Long loanProgramID) {
		this.loanProgramID = loanProgramID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
