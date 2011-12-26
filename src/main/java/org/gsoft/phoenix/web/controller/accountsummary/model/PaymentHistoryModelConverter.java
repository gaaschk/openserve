package org.gsoft.phoenix.web.controller.accountsummary.model;

import java.util.ArrayList;
import java.util.List;

import org.gsoft.phoenix.domain.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentHistoryModelConverter {
	public PaymentHistoryModel convertToModel(List<Payment> payments){
		PaymentHistoryModel paymentHistoryModel = new PaymentHistoryModel();
		paymentHistoryModel.setPayments(new ArrayList<PaymentModel>());
		for(Payment payment: payments){
			PaymentModel paymentModel = new PaymentModel();
			paymentModel.setPaymentEffectiveDate(payment.getEffectiveDate());
			paymentModel.setPaymentPostDate(payment.getPostDate());
			paymentModel.setPaymentAmount(payment.getPaymentAmount());
			paymentHistoryModel.getPayments().add(paymentModel);
		}
		return paymentHistoryModel;
	}
}
