package org.gsoft.openserv.web.loanprogram.repaymentplan.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum RepaymentPlanType {
	STANDARD(10L, "Standard"),
	FIXED(20L, "Fixed");
	
	private long id;
	private String label;
	
	private RepaymentPlanType(long id, String label) {
		this.id = id;
		this.label = label;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING)
	public long getID(){
		return id;
	}
	
	public String getLabel(){
		return label;
	}

	public static RepaymentPlanType forID(Long id) {
		for (final RepaymentPlanType repaymentPlanType : values()) {
			if (repaymentPlanType.getID() == id) {
				return repaymentPlanType;
			}
		}
		return null;
	}
}
