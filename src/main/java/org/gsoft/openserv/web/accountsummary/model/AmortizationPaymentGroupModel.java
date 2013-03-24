package org.gsoft.openserv.web.accountsummary.model;

import java.io.Serializable;

import org.gsoft.openserv.web.support.formatter.currency.CurrencyInPenniesFormat;

public class AmortizationPaymentGroupModel implements Serializable{
	private static final long serialVersionUID = 6937606246921123365L;

	private Integer paymentAmount;
	private Integer paymentCount;

	public Integer getPaymentAmount() {
		return paymentAmount;
	}
	@CurrencyInPenniesFormat
	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Integer getPaymentCount() {
		return paymentCount;
	}
	public void setPaymentCount(Integer paymentCount) {
		this.paymentCount = paymentCount;
	}
}
