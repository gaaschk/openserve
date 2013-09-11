package org.gsoft.openserv.web.loanprogram.repaymentplan.model;

public class RepaymentPlanModel {
	private Long repaymentPlanID;
	private Long defaultLoanProgramSettingsID;
	private Long planStartDateID;
	private Long planTypeID;
	private Boolean capInterestAtEnd;
	private Long capFrequencyID;
	private Integer graceMonths;
	private Integer maxLoanTerm;
	private Integer minPaymentAmount;
	private Integer paymentAmount;
	
	public Long getRepaymentPlanID() {
		return repaymentPlanID;
	}
	public void setRepaymentPlanID(Long repaymentPlanID) {
		this.repaymentPlanID = repaymentPlanID;
	}
	public Long getDefaultLoanProgramSettingsID() {
		return defaultLoanProgramSettingsID;
	}
	public void setDefaultLoanProgramSettingsID(
			Long defaultLoanProgramSettingsID) {
		this.defaultLoanProgramSettingsID = defaultLoanProgramSettingsID;
	}
	public Long getPlanStartDateID() {
		return planStartDateID;
	}
	public void setPlanStartDateID(Long planStartDate) {
		this.planStartDateID = planStartDate;
	}
	public Long getPlanTypeID() {
		return planTypeID;
	}
	public void setPlanTypeID(Long planTypeID) {
		this.planTypeID = planTypeID;
	}
	public Boolean getCapInterestAtEnd() {
		return capInterestAtEnd;
	}
	public void setCapInterestAtEnd(Boolean capInterestAtEnd) {
		this.capInterestAtEnd = capInterestAtEnd;
	}
	public Long getCapFrequencyID() {
		return capFrequencyID;
	}
	public void setCapFrequencyID(Long capFrequency) {
		this.capFrequencyID = capFrequency;
	}
	public Integer getGraceMonths() {
		return graceMonths;
	}
	public void setGraceMonths(Integer graceMonths) {
		this.graceMonths = graceMonths;
	}
	public Integer getMaxLoanTerm() {
		return maxLoanTerm;
	}
	public void setMaxLoanTerm(Integer maxLoanTerm) {
		this.maxLoanTerm = maxLoanTerm;
	}
	public Integer getMinPaymentAmount() {
		return minPaymentAmount;
	}
	public void setMinPaymentAmount(Integer minPaymentAmount) {
		this.minPaymentAmount = minPaymentAmount;
	}
	public Integer getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
}
