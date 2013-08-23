package org.gsoft.openserv.web.loanprogram.converter;

import java.math.BigDecimal;

import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.web.loanprogram.model.DefaultLoanProgramSettingsModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DefaultLoanProgramSettingsToDefaultLoanProgramSettingsModelConverter implements Converter<DefaultLoanProgramSettings, DefaultLoanProgramSettingsModel>{

	@Override
	public DefaultLoanProgramSettingsModel convert(DefaultLoanProgramSettings source) {
		DefaultLoanProgramSettingsModel model = new DefaultLoanProgramSettingsModel();
		model.setDefaultLoanProgramSettingsID(source.getDefaultLoanProgramSettingsID());
		model.setLoanProgramID(source.getLoanProgram().getLoanProgramID());
		model.setBaseRateUpdateFrequency(String.valueOf(source.getBaseRateUpdateFrequency().getID()));
		model.setDaysBeforeDueToBill(source.getDaysBeforeDueToBill());
		model.setDaysLateForFee(source.getDaysLateForFee());
		model.setEffectiveDate(source.getEffectiveDate());
		model.setEndDate(source.getEndDate());
		model.setGraceMonths(source.getGraceMonths());
		model.setIsVariableRate(source.isVariableRate());
		model.setLateFeeAmount(new BigDecimal(source.getLateFeeAmount()).divide(new BigDecimal(100)));
		model.setMaximumLoanTerm(source.getMaximumLoanTerm());
		model.setMinDaysToFirstDue(source.getMinDaysToFirstDue());
		model.setPrepaymentDays(source.getPrepaymentDays());
		return model;
	}

}
