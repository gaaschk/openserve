package org.gsoft.phoenix.web.converters;

import org.gsoft.phoenix.domain.payment.BillingStatement;
import org.gsoft.phoenix.web.models.BillingStatementModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BillingStatementToBillingStatementModelConverter implements Converter<BillingStatement, BillingStatementModel>{

	@Override
	public BillingStatementModel convert(BillingStatement source) {
		BillingStatementModel model = new BillingStatementModel();
		model.setCreatedDate(source.getCreatedDate());
		model.setDueDate(source.getDueDate());
		model.setPaidAmount(source.getPaidAmount());
		model.setMinimumRequiredPayment(source.getMinimumRequiredPayment());
		model.setSatisfiedDate(source.getSatisfiedDate());
		return model;
	}

}
