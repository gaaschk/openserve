package org.gsoft.openserv.web.loanprogram.repaymentplan.converter;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanPhaseEvent;
import org.gsoft.openserv.domain.repayment.RepaymentPlanSettings;
import org.gsoft.openserv.repositories.repayment.RepaymentPlanSettingsRepository;
import org.gsoft.openserv.util.time.FrequencyType;
import org.gsoft.openserv.web.loanprogram.repaymentplan.model.RepaymentPlanSettingsModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RepaymentPlanSettingsModelToRepaymentPlanSettingsConverter
		implements Converter<RepaymentPlanSettingsModel, RepaymentPlanSettings> {
	@Resource
	private RepaymentPlanSettingsRepository repository;
	
	@Override
	public RepaymentPlanSettings convert(RepaymentPlanSettingsModel source) {
		Long id = source.getRepaymentPlanSettingsID();
		RepaymentPlanSettings settings = null;
		if(id == null || id < 0){
			settings = new RepaymentPlanSettings();
			repository.save(settings);
		}
		else{
			settings = repository.findOne(id);
		}
		settings.setCapFrequency(FrequencyType.forID(source.getCapFrequencyID()));
		settings.setCapInterestAtBegin(source.getCapInterestAtBegin());
		settings.setDescription(source.getDescription());
		settings.setName(source.getName());
		settings.setPlanStartDate(LoanPhaseEvent.forID(source.getPlanStartDateID()));
		return settings;
	}

}
