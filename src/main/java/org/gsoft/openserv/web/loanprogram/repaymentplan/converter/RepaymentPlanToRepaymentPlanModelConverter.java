package org.gsoft.openserv.web.loanprogram.repaymentplan.converter;

import org.gsoft.openserv.domain.repayment.FixedRepaymentPlan;
import org.gsoft.openserv.domain.repayment.RepaymentPlan;
import org.gsoft.openserv.domain.repayment.StandardRepaymentPlan;
import org.gsoft.openserv.web.loanprogram.repaymentplan.model.RepaymentPlanModel;
import org.gsoft.openserv.web.loanprogram.repaymentplan.model.RepaymentPlanType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RepaymentPlanToRepaymentPlanModelConverter
		implements Converter<RepaymentPlan, RepaymentPlanModel> {

	@Override
	public RepaymentPlanModel convert(RepaymentPlan source) {
		RepaymentPlanModel model = new RepaymentPlanModel();
		model.setRepaymentPlanID(source.getRepaymentPlanID());
		if(source.getPlanStartDate() != null)
			model.setPlanStartDateID(source.getPlanStartDate().getID());
		model.setGraceMonths(source.getGraceMonths());
		if(source instanceof StandardRepaymentPlan){
			model.setPlanTypeID(RepaymentPlanType.STANDARD.getID());
			model.setMaxLoanTerm(((StandardRepaymentPlan) source).getMaxLoanTerm());
			model.setMinPaymentAmount(((StandardRepaymentPlan) source).getMinPaymentAmount());
		}
		else if(source instanceof FixedRepaymentPlan){
			model.setPlanTypeID(RepaymentPlanType.FIXED.getID());
			if(((FixedRepaymentPlan) source).getCapFrequency() != null)
				model.setCapFrequencyID(((FixedRepaymentPlan) source).getCapFrequency().getID());
			model.setCapInterestAtEnd(((FixedRepaymentPlan) source).getCapAtEnd());
			model.setPaymentAmount(((FixedRepaymentPlan) source).getPaymentAmount());
		}
		return model;
	}

}
