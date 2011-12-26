package org.gsoft.phoenix.web.controller.accountsummary.model;

import java.util.List;

public class PaymentHistoryModel {
	private List<PaymentModel> payments;

	public List<PaymentModel> getPayments() {
		return payments;
	}
	public void setPayments(List<PaymentModel> payments) {
		this.payments = payments;
	}
}
