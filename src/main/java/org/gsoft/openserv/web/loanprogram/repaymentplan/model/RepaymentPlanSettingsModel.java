package org.gsoft.openserv.web.loanprogram.repaymentplan.model;

public class RepaymentPlanSettingsModel {
	private Long repaymentPlanSettingsID;
	private String name;
	private String description;
	private Long planStartDateID;
	private Boolean capInterestAtBegin;
	private Long capFrequencyID;
	
	public Long getRepaymentPlanSettingsID() {
		return repaymentPlanSettingsID;
	}
	public void setRepaymentPlanSettingsID(Long repaymentPlanSettingsID) {
		this.repaymentPlanSettingsID = repaymentPlanSettingsID;
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
	public Long getPlanStartDateID() {
		return planStartDateID;
	}
	public void setPlanStartDateID(Long planStartDate) {
		this.planStartDateID = planStartDate;
	}
	public Boolean getCapInterestAtBegin() {
		return capInterestAtBegin;
	}
	public void setCapInterestAtBegin(Boolean capInterestAtBegin) {
		this.capInterestAtBegin = capInterestAtBegin;
	}
	public Long getCapFrequencyID() {
		return capFrequencyID;
	}
	public void setCapFrequencyID(Long capFrequency) {
		this.capFrequencyID = capFrequency;
	}
}
