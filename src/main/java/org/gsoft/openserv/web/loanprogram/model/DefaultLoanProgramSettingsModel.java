package org.gsoft.openserv.web.loanprogram.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultLoanProgramSettingsModel implements Serializable{
	private static final long serialVersionUID = 8854407098839115592L;

	private Long defaultLoanProgramSettingsID;
	private Date effectiveDate;
	private Date endDate;
	private Integer maximumLoanTerm;
	private Integer graceMonths;
	private Integer minDaysToFirstDue;
	private Integer prepaymentDays;
	private Integer daysBeforeDueToBill;
	private Integer daysLateForFee;
	private Integer lateFeeAmount;
	private Boolean isVariableRate;
	
	public Long getDefaultLoanProgramSettingsID() {
		return defaultLoanProgramSettingsID;
	}
	public void setDefaultLoanProgramSettingsID(Long defaultLoanProgramSettingsID) {
		this.defaultLoanProgramSettingsID = defaultLoanProgramSettingsID;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getMaximumLoanTerm() {
		return maximumLoanTerm;
	}
	public void setMaximumLoanTerm(Integer maximumLoanTerm) {
		this.maximumLoanTerm = maximumLoanTerm;
	}
	public Integer getGraceMonths() {
		return graceMonths;
	}
	public void setGraceMonths(Integer graceMonths) {
		this.graceMonths = graceMonths;
	}
	public Integer getMinDaysToFirstDue() {
		return minDaysToFirstDue;
	}
	public void setMinDaysToFirstDue(Integer minDaysToFirstDue) {
		this.minDaysToFirstDue = minDaysToFirstDue;
	}
	public Integer getPrepaymentDays() {
		return prepaymentDays;
	}
	public void setPrepaymentDays(Integer prepaymentDays) {
		this.prepaymentDays = prepaymentDays;
	}
	public Integer getDaysBeforeDueToBill() {
		return daysBeforeDueToBill;
	}
	public void setDaysBeforeDueToBill(Integer daysBeforeDueToBill) {
		this.daysBeforeDueToBill = daysBeforeDueToBill;
	}
	public Integer getDaysLateForFee() {
		return daysLateForFee;
	}
	public void setDaysLateForFee(Integer daysLateForFee) {
		this.daysLateForFee = daysLateForFee;
	}
	public Integer getLateFeeAmount() {
		return lateFeeAmount;
	}
	public void setLateFeeAmount(Integer lateFeeAmount) {
		this.lateFeeAmount = lateFeeAmount;
	}
	public Boolean getIsVariableRate() {
		return isVariableRate;
	}
	public void setIsVariableRate(Boolean isVariableRate) {
		this.isVariableRate = isVariableRate;
	}
}
