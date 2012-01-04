package org.gsoft.phoenix.web.controller.payment.model;

import java.io.Serializable;
import java.util.Date;

import org.gsoft.phoenix.web.controller.addloan.model.PersonModel;

public class PaymentEntryModel implements Serializable{
	private static final long serialVersionUID = -2289069982845305280L;
	private PersonModel theBorrower;
	private Integer paymentAmount;
	private Date paymentEffectiveDate;
	private Date paymentPostDate;
	
	public PersonModel getTheBorrower() {
		return theBorrower;
	}
	public void setTheBorrower(PersonModel theBorrower) {
		this.theBorrower = theBorrower;
	}
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
