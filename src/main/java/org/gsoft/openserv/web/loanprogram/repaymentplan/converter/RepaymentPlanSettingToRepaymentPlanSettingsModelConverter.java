package org.gsoft.openserv.web.loanprogram.repaymentplan.converter;

import org.gsoft.openserv.domain.repayment.RepaymentPlanSettings;
import org.gsoft.openserv.web.loanprogram.repaymentplan.model.RepaymentPlanSettingsModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RepaymentPlanSettingToRepaymentPlanSettingsModelConverter
		implements Converter<RepaymentPlanSettings, RepaymentPlanSettingsModel> {

	@Override
	public RepaymentPlanSettingsModel convert(RepaymentPlanSettings source) {
		RepaymentPlanSettingsModel model = new RepaymentPlanSettingsModel();
		model.setCapFrequencyID(source.getCapFrequency().getID());
		model.setCapInterestAtBegin(source.getCapInterestAtBegin());
		model.setDescription(source.getDescription());
		model.setName(source.getName());
		model.setPlanStartDateID(source.getPlanStartDate().getID());
		model.setRepaymentPlanSettingsID(source.getRepaymentPlanSettingsID());
		return model;
	}

}
