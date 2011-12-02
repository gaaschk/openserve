package org.gsoft.phoenix.web.controller.payment.model;

import org.gsoft.phoenix.web.controller.addloan.model.PersonModel;

public class PaymentModel {
	private PersonModel theBorrower;
	private Integer paymentAmount;
	
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
}
