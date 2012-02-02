package org.gsoft.openserv.web.converters;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.web.models.PaymentHistoryModel;
import org.gsoft.openserv.web.models.PaymentModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentListToPaymentHistoryModelConverter implements Converter<List<Payment>, PaymentHistoryModel>{
	@Resource
	private ConversionService conversionService;
	
	public PaymentHistoryModel convert(List<Payment> payments){
		PaymentHistoryModel paymentHistoryModel = new PaymentHistoryModel();
		paymentHistoryModel.setPayments(new ArrayList<PaymentModel>());
		for(Payment payment: payments){
			paymentHistoryModel.getPayments().add(conversionService.convert(payment, PaymentModel.class));
		}
		return paymentHistoryModel;
	}
}
