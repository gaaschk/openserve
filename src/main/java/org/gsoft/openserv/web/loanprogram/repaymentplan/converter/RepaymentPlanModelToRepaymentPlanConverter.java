package org.gsoft.openserv.web.loanprogram.repaymentplan.converter;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanPhaseEvent;
import org.gsoft.openserv.domain.repayment.FixedRepaymentPlan;
import org.gsoft.openserv.domain.repayment.RepaymentPlan;
import org.gsoft.openserv.domain.repayment.StandardRepaymentPlan;
import org.gsoft.openserv.repositories.loan.DefaultLoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.repayment.FixedRepaymentPlanRepository;
import org.gsoft.openserv.repositories.repayment.StandardRepaymentPlanRepository;
import org.gsoft.openserv.util.time.FrequencyType;
import org.gsoft.openserv.web.loanprogram.repaymentplan.model.RepaymentPlanModel;
import org.gsoft.openserv.web.loanprogram.repaymentplan.model.RepaymentPlanType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RepaymentPlanModelToRepaymentPlanConverter
		implements Converter<RepaymentPlanModel, RepaymentPlan> {
	@Resource
	private StandardRepaymentPlanRepository standardPlanRepository;
	@Resource
	private FixedRepaymentPlanRepository fixedPlanRepository;
	@Resource
	private DefaultLoanProgramSettingsRepository defaultSettingsRepository;
	
	@Override
	public RepaymentPlan convert(RepaymentPlanModel source) {
		RepaymentPlan settings = null;
		RepaymentPlanType type = RepaymentPlanType.forID(source.getPlanTypeID());
		if(type == RepaymentPlanType.STANDARD){
			if(source.getRepaymentPlanID() == null || source.getRepaymentPlanID() < 0){
				settings = new StandardRepaymentPlan();
				settings = standardPlanRepository.save((StandardRepaymentPlan)settings);
			}
			else{
				settings = standardPlanRepository.findOne(source.getRepaymentPlanID());
			}
			((StandardRepaymentPlan)settings).setMaxLoanTerm(source.getMaxLoanTerm());
			((StandardRepaymentPlan)settings).setMinPaymentAmount(source.getMinPaymentAmount());
		}else if(type == RepaymentPlanType.FIXED){
			if(source.getRepaymentPlanID() == null || source.getRepaymentPlanID() < 0){
				settings = new FixedRepaymentPlan();
				settings = fixedPlanRepository.save((FixedRepaymentPlan)settings);
			}
			else{
				settings = fixedPlanRepository.findOne(source.getRepaymentPlanID());
			}
			((FixedRepaymentPlan)settings).setCapAtEnd(source.getCapInterestAtEnd());
			((FixedRepaymentPlan)settings).setCapFrequency(FrequencyType.forID(source.getCapFrequencyID()));
			((FixedRepaymentPlan)settings).setPaymentAmount(source.getPaymentAmount());
		}
		settings.setPlanStartDate(LoanPhaseEvent.forID(source.getPlanStartDateID()));
		settings.setDefaultLoanProgramSettings(defaultSettingsRepository.findOne(source.getDefaultLoanProgramSettingsID()));
		settings.setGraceMonths(source.getGraceMonths());
		return settings;
	}

}
