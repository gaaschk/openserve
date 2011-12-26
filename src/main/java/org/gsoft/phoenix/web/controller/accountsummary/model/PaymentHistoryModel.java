package org.gsoft.phoenix.web.controller.accountsummary.model;

import java.util.List;

import org.gsoft.phoenix.web.controller.payment.model.PaymentModel;

public class PaymentHistoryModel {
	private List<PaymentModel> payments;

	public List<PaymentModel> getPayments() {
		return payments;
	}
	public void setPayments(List<PaymentModel> payments) {
		this.payments = payments;
	}
}
