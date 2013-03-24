package org.gsoft.openserv.web.accountsummary.model;

import java.io.Serializable;
import java.util.Date;

import org.gsoft.openserv.web.support.formatter.currency.CurrencyInPenniesFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class BillingStatementModel implements Serializable{
	private static final long serialVersionUID = 831721439085817946L;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dueDate;
	private Integer minimumRequiredPayment;
	private Integer paidAmount;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date satisfiedDate;
	private Integer lateFeeAmount;
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	@CurrencyInPenniesFormat
	public Integer getMinimumRequiredPayment() {
		return minimumRequiredPayment;
	}
	public void setMinimumRequiredPayment(Integer minimumRequiredPayment) {
		this.minimumRequiredPayment = minimumRequiredPayment;
	}
	@CurrencyInPenniesFormat
	public Integer getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(Integer paidAmount) {
		this.paidAmount = paidAmount;
	}
	public Date getSatisfiedDate() {
		return satisfiedDate;
	}
	public void setSatisfiedDate(Date satisfiedDate) {
		this.satisfiedDate = satisfiedDate;
	}
	public Integer getLateFeeAmount() {
		return lateFeeAmount;
	}
	@CurrencyInPenniesFormat
	public void setLateFeeAmount(Integer lateFeeAmount) {
		this.lateFeeAmount = lateFeeAmount;
	}
}
