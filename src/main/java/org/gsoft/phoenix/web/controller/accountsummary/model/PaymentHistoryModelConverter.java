package org.gsoft.phoenix.web.controller.accountsummary.model;

import java.util.ArrayList;
import java.util.List;

import org.gsoft.phoenix.domain.payment.LoanPayment;
import org.gsoft.phoenix.domain.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentHistoryModelConverter {
	public PaymentHistoryModel convertToModel(List<Payment> payments){
		PaymentHistoryModel paymentHistoryModel = new PaymentHistoryModel();
		paymentHistoryModel.setPayments(new ArrayList<PaymentModel>());
		for(Payment payment: payments){
			paymentHistoryModel.getPayments().add(this.convertToModel(payment));
		}
		return paymentHistoryModel;
	}
	
	public PaymentModel convertToModel(Payment payment){
		PaymentModel paymentModel = new PaymentModel();
		paymentModel.setPaymentID(payment.getPaymentID());
		paymentModel.setPaymentEffectiveDate(payment.getEffectiveDate());
		paymentModel.setPaymentPostDate(payment.getPostDate());
		paymentModel.setPaymentAmount(payment.getPaymentAmount());
		List<LoanPayment> loanPayments = payment.getLoanPayments();
		paymentModel.setLoanPayments(new ArrayList<LoanPaymentModel>());
		for(LoanPayment loanPayment:loanPayments){
			paymentModel.getLoanPayments().add(this.convertToModel(loanPayment));
		}
		return paymentModel;
	}
	
	public LoanPaymentModel convertToModel(LoanPayment loanPayment){
		LoanPaymentModel loanPaymentModel = new LoanPaymentModel();
		loanPaymentModel.setAppliedAmount(loanPayment.getAppliedAmount());
		loanPaymentModel.setLoanID(loanPayment.getLoanID());
		return loanPaymentModel;
	}
}
