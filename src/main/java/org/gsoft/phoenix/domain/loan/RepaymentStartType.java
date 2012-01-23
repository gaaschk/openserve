package org.gsoft.phoenix.domain.loan;

import org.gsoft.phoenix.util.jpa.PhoenixEnum;

public enum RepaymentStartType implements PhoenixEnum<RepaymentStartType> {
	FIRST_DISBUREMENT,
	LAST_DISBURSEMENT;
	
	private Long repaymentStartTypeID;
	private String name;
	private String description;
	
	public Long getID() {
		return this.getRepaymentStartTypeID();
	}

	public static RepaymentStartType forID(Long id) {
		for (final RepaymentStartType rpsType : values()) {
			if (rpsType.getID().equals(id)) {
				return rpsType;
			}
		}
		return null;
	}

	public Long getRepaymentStartTypeID() {
		return repaymentStartTypeID;
	}
	public void setRepaymentStartTypeID(Long repaymentStartTypeID) {
		this.repaymentStartTypeID = repaymentStartTypeID;
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
