package org.gsoft.phoenix.web.models;

import java.io.Serializable;
import java.util.Date;

import org.gsoft.phoenix.util.formatter.CurrencyInPenniesFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class BillingStatementModel implements Serializable{
	private static final long serialVersionUID = 831721439085817946L;
	private Date createdDate;
	private Date dueDate;
	private Integer minimumRequiredPayment;
	private Integer paidAmount;
	private Date satisfiedDate;
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
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
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getSatisfiedDate() {
		return satisfiedDate;
	}
	public void setSatisfiedDate(Date satisfiedDate) {
		this.satisfiedDate = satisfiedDate;
	}
}
