package org.gsoft.phoenix.web.converters;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.amortization.AmortizationLoanPayment;
import org.gsoft.phoenix.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.phoenix.web.models.AmortizationPaymentGroupModel;
import org.gsoft.phoenix.web.models.LoanAmortizationModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanAmortizationScheduleToLoanAmortizationModelConverter implements Converter<LoanAmortizationSchedule, LoanAmortizationModel>{
	@Resource
	private ConversionService conversionService;
	
	@Override
	public LoanAmortizationModel convert(LoanAmortizationSchedule source) {
		LoanAmortizationModel model = new LoanAmortizationModel();
		model.setCreationDate(source.getAmortizationSchedule().getCreationDate());
		model.setEffectiveDate(source.getAmortizationSchedule().getEffectiveDate());
		model.setPaymentGroups(new ArrayList<AmortizationPaymentGroupModel>());
		for(AmortizationLoanPayment payment:source.getAmortizationLoanPayments()){
			model.getPaymentGroups().add(conversionService.convert(payment, AmortizationPaymentGroupModel.class));
		}
		return model;
	}

}