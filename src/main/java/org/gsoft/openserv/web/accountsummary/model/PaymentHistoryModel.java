package org.gsoft.openserv.web.accountsummary.model;

import java.io.Serializable;
import java.util.List;

public class PaymentHistoryModel implements Serializable{
	private static final long serialVersionUID = -368093836942321581L;
	private List<PaymentModel> payments;

	public List<PaymentModel> getPayments() {
		return payments;
	}
	public void setPayments(List<PaymentModel> payments) {
		this.payments = payments;
	}
}
