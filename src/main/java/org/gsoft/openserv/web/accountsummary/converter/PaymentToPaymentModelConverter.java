package org.gsoft.openserv.web.accountsummary.converter;

import java.util.ArrayList;
import java.util.List;

import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.web.accountsummary.model.LoanPaymentModel;
import org.gsoft.openserv.web.accountsummary.model.PaymentModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentToPaymentModelConverter implements Converter<Payment, PaymentModel> {
	public PaymentModel convert(Payment payment){
		PaymentModel paymentModel = new PaymentModel();
		paymentModel.setPaymentID(payment.getPaymentID());
		paymentModel.setPaymentEffectiveDate(payment.getEffectiveDate());
		paymentModel.setPaymentPostDate(payment.getPostDate());
		paymentModel.setPaymentAmount(payment.getPaymentAmount());
		List<LoanPayment> loanPayments = payment.getLoanPayments();
		paymentModel.setLoanPayments(new ArrayList<LoanPaymentModel>());
		for(LoanPayment loanPayment:loanPayments){
			paymentModel.getLoanPayments().add(this.convert(loanPayment));
		}
		return paymentModel;
	}
	
	private LoanPaymentModel convert(LoanPayment loanPayment){
		LoanPaymentModel loanPaymentModel = new LoanPaymentModel();
		loanPaymentModel.setAppliedAmount(loanPayment.getAppliedAmount());
		loanPaymentModel.setLoanID(loanPayment.getLoanID());
		return loanPaymentModel;
	}

}
