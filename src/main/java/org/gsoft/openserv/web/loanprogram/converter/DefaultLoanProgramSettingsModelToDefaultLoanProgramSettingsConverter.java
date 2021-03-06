package org.gsoft.openserv.web.loanprogram.converter;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.domain.loan.RepaymentStartType;
import org.gsoft.openserv.repositories.loan.DefaultLoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.loan.LoanProgramRepository;
import org.gsoft.openserv.repositories.rates.RateRepository;
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
	@Resource
	private LoanProgramRepository loanProgramRepository;
	@Resource
	private RateRepository rateRepository;
	
	@Override
	public DefaultLoanProgramSettings convert(DefaultLoanProgramSettingsModel model){
		Long id = model.getDefaultLoanProgramSettingsID();
		DefaultLoanProgramSettings lp = null;
		if(id == null || id < 0){
			lp = new DefaultLoanProgramSettings();
			LoanProgram loanProgram = loanProgramRepository.findOne(model.getLoanProgramID());
			lp.setLoanProgram(loanProgram);
			defaultLoanProgramSettingsRepository.save(lp);
		}
		else{
			lp = defaultLoanProgramSettingsRepository.findOne(id);
		}
		lp.setBaseRateUpdateFrequency(FrequencyType.forID(Long.valueOf(model.getBaseRateUpdateFrequency())));
		lp.setDaysBeforeDueToBill(model.getDaysBeforeDueToBill());
		lp.setDaysLateForFee(model.getDaysLateForFee());
		lp.setEffectiveDate(model.getEffectiveDate());
		lp.setGraceMonths(model.getGraceMonths());
		lp.setLateFeeAmount(model.getLateFeeAmount().multiply(new BigDecimal(100)).intValue());
		lp.setMaximumLoanTerm(model.getMaximumLoanTerm());
		lp.setMinDaysToFirstDue(model.getMinDaysToFirstDue());
		lp.setPrepaymentDays(model.getPrepaymentDays());
		lp.setRepaymentStartType(RepaymentStartType.forID(Long.valueOf(model.getRepaymentStartType())));
		lp.setBaseRate(rateRepository.findOne(Long.valueOf(model.getBaseRate())));
		return lp;
	}
}
