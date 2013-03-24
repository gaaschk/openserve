package org.gsoft.openserv.web.accountsummary.converter;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.amortization.AmortizationLoanPayment;
import org.gsoft.openserv.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.openserv.web.accountsummary.model.AmortizationPaymentGroupModel;
import org.gsoft.openserv.web.accountsummary.model.LoanAmortizationModel;
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
