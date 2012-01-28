package org.gsoft.phoenix.web.converters;

import org.gsoft.phoenix.domain.amortization.AmortizationLoanPayment;
import org.gsoft.phoenix.web.models.AmortizationPaymentGroupModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AmortizationLoanPaymentToAmortizationPaymentGroupModelConverter implements Converter<AmortizationLoanPayment,AmortizationPaymentGroupModel>{

	@Override
	public AmortizationPaymentGroupModel convert(AmortizationLoanPayment source) {
		AmortizationPaymentGroupModel model = new AmortizationPaymentGroupModel();
		model.setPaymentAmount(source.getPaymentAmount());
		model.setPaymentCount(source.getPaymentCount());
		return model;
	}

}
