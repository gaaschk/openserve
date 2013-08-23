package org.gsoft.openserv.web.loanprogram.converter;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.repositories.loan.DefaultLoanProgramSettingsRepository;
import org.gsoft.openserv.service.loanprogram.LoanProgramSettingsService;
import org.gsoft.openserv.util.time.FrequencyType;
import org.gsoft.openserv.web.loanprogram.model.DefaultLoanProgramSettingsModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DefaultLoanProgramSettingsModelToDefaultLoanProgramSettingsConverter implements Converter<DefaultLoanProgramSettingsModel, DefaultLoanProgramSettings>{
	@Resource
	private DefaultLoanProgramSettingsRepository defaultLoanProgramSettingsRepository;
	@Resource
	private LoanProgramSettingsService loanProgramSettingsService;
	
	@Override
	public DefaultLoanProgramSettings convert(DefaultLoanProgramSettingsModel model){
		Long id = model.getDefaultLoanProgramSettingsID();
		DefaultLoanProgramSettings lp = null;
		if(id == null || id < 0){
			lp = new DefaultLoanProgramSettings();
			defaultLoanProgramSettingsRepository.save(lp);
		}
		else{
			lp = defaultLoanProgramSettingsRepository.findOne(id);
		}
		lp.setDefaultLoanProgramSettingsID(model.getDefaultLoanProgramSettingsID());
		lp.setBaseRateUpdateFrequency(FrequencyType.forID(Long.valueOf(model.getBaseRateUpdateFrequency())));
		lp.setDaysBeforeDueToBill(model.getDaysBeforeDueToBill());
		lp.setDaysLateForFee(model.getDaysLateForFee());
		lp.setEffectiveDate(model.getEffectiveDate());
		lp.setEndDate(model.getEndDate());
		lp.setGraceMonths(model.getGraceMonths());
		lp.setVariableRate(model.getIsVariableRate());
		lp.setLateFeeAmount(model.getLateFeeAmount().multiply(new BigDecimal(100)).intValue());
		lp.setMaximumLoanTerm(model.getMaximumLoanTerm());
		lp.setMinDaysToFirstDue(model.getMinDaysToFirstDue());
		lp.setPrepaymentDays(model.getPrepaymentDays());
		return lp;
	
	}
}
