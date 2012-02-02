package org.gsoft.openserv.domain.loan;

import org.gsoft.openserv.util.jpa.OpenServEnum;

public enum LoanType implements OpenServEnum<LoanType>{
	PRIVATE_STUDENT(10L, "PRIVATE_STUDENT", "Private Student Loan"), 
	MORTGAGE(20L, "MORTGAGE", "Mortgage Loan");

	private Long loanTypeID;
	private String name;
	private String description;

	LoanType(Long loanTypeID, String loanTypeName,
			String loanTypeDesc) {
		this.setLoanTypeID(loanTypeID);
		this.setName(loanTypeName);
		this.setDescription(loanTypeDesc);
	}


	public Long getID() {
		return this.getLoanTypeID();
	}

	public static LoanType forID(Long id) {
		for (final LoanType loanType : values()) {
			if (loanType.getID().equals(id)) {
				return loanType;
			}
		}
		return null;
	}

	public Long getLoanTypeID() {
		return loanTypeID;
	}

	public void setLoanTypeID(Long loanTypeID) {
		this.loanTypeID = loanTypeID;
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
