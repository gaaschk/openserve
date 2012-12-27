package org.gsoft.openserv.domain.loan;

import org.gsoft.openserv.util.jpa.OpenServEnum;

public enum LoanAdjustmentType implements OpenServEnum<LoanType>{
	DISBURSEMENT(10L, "Disbursement", "Disbursement Made"),
	MANUAL_FIXED_ADJ(20L, "Manual Fixed Adjustment", "Fixed Adjusement applied manually"),
	PAYMENT(30L, "Payment", "Payment Applied");

	private Long loanAdjustmentTypeID;
	private String name;
	private String description;
	
	LoanAdjustmentType(Long id, String name, String desc){
		this.loanAdjustmentTypeID = id;
		this.name = name;
		this.description = desc;
	}

	public Long getLoanAdjustmentTypeID() {
		return loanAdjustmentTypeID;
	}

	public void setLoanAdjustmentTypeID(Long loanAdjustmentTypeID) {
		this.loanAdjustmentTypeID = loanAdjustmentTypeID;
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
	
	public Long getID(){
		return this.getLoanAdjustmentTypeID();
	}
	
	public static LoanAdjustmentType forID(Long id) {
		for (final LoanAdjustmentType loanAdjustmentType : values()) {
			if (loanAdjustmentType.getID().equals(id)) {
				return loanAdjustmentType;
			}
		}
		return null;
	}
}
