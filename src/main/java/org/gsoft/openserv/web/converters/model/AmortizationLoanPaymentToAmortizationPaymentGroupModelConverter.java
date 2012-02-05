package org.gsoft.openserv.web.converters.model;

import org.gsoft.openserv.domain.amortization.AmortizationLoanPayment;
import org.gsoft.openserv.web.models.AmortizationPaymentGroupModel;
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
