package org.gsoft.openserv.web.payment.model;

import java.io.Serializable;
import java.util.Date;

import org.gsoft.openserv.web.person.model.PersonModel;
import org.gsoft.openserv.web.support.formatter.currency.CurrencyInPenniesFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class PaymentEntryModel implements Serializable{
	private static final long serialVersionUID = -2289069982845305280L;
	private PersonModel theBorrower;
	private Integer paymentAmount;
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date paymentEffectiveDate = new Date();
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date paymentPostDate;
	
	public PersonModel getTheBorrower() {
		return theBorrower;
	}
	public void setTheBorrower(PersonModel theBorrower) {
		this.theBorrower = theBorrower;
	}
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
