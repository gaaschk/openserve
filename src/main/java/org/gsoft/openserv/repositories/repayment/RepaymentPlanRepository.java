package org.gsoft.openserv.repositories.repayment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.repayment.RepaymentPlan;
import org.springframework.stereotype.Repository;

@Repository
public class RepaymentPlanRepository {
	@Resource
	private StandardRepaymentPlanRepository standardPlanRepository;
	@Resource
	private FixedRepaymentPlanRepository fixedPlanRepository;
	
	public List<RepaymentPlan> findAllRepaymentPlansForDefaultLoanSettings(Long loanSettingsID){
		List<RepaymentPlan> allPlans = new ArrayList<>();
		allPlans.addAll(standardPlanRepository.findAllRepaymentPlansForDefaultLoanSettings(loanSettingsID));
		allPlans.addAll(fixedPlanRepository.findAllRepaymentPlansForDefaultLoanSettings(loanSettingsID));
		return allPlans;
	}
}
