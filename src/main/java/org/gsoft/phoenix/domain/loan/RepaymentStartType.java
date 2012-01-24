package org.gsoft.phoenix.domain.loan;

import org.gsoft.phoenix.util.jpa.PhoenixEnum;

public enum RepaymentStartType implements PhoenixEnum<RepaymentStartType> {
	FIRST_DISBUREMENT(10L, "FIRST_DISBURSEMENT", "Repayment begins after first disbursement."),
	LAST_DISBURSEMENT(20L, "LAST_DISBURSEMENT", "Repayment begins after last disbursement.");
	
	private Long repaymentStartTypeID;
	private String name;
	private String description;
	
	RepaymentStartType(Long id, String name, String desc){
		this.repaymentStartTypeID = id;
		this.name = name;
		this.description = desc;
	}
	
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
