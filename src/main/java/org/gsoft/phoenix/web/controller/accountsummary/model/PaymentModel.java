package org.gsoft.phoenix.web.controller.accountsummary.model;

import java.util.Date;

import org.gsoft.phoenix.util.formatter.CurrencyInPenniesFormat;

public class PaymentModel {
	private Integer paymentAmount;
	private Date paymentEffectiveDate;
	private Date paymentPostDate;
	
	@CurrencyInPenniesFormat
	public Integer getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Date getPaymentEffectiveDate() {
		return paymentEffectiveDate;
	}
	public void setPaymentEffectiveDate(Date paymentEffectiveDate) {
		this.paymentEffectiveDate = paymentEffectiveDate;
	}
	public Date getPaymentPostDate() {
		return paymentPostDate;
	}
	public void setPaymentPostDate(Date paymentPostDate) {
		this.paymentPostDate = paymentPostDate;
	}
}
