package org.gsoft.openserv.service.repaymentplan;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.repayment.FixedRepaymentPlan;
import org.gsoft.openserv.domain.repayment.RepaymentPlan;
import org.gsoft.openserv.domain.repayment.StandardRepaymentPlan;
import org.gsoft.openserv.repositories.repayment.FixedRepaymentPlanRepository;
import org.gsoft.openserv.repositories.repayment.StandardRepaymentPlanRepository;
import org.gsoft.openserv.rulesengine.annotation.RunRulesEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class RepaymentPlanSettingsService {
	@Resource
	private StandardRepaymentPlanRepository standardRepository;
	@Resource
	private FixedRepaymentPlanRepository fixedRepository;
	
	@Transactional
	@RunRulesEngine
	public List<RepaymentPlan> saveRepaymentPlanSettings(List<RepaymentPlan> dirtySettings){
		List<RepaymentPlan> savedSettingsList = new ArrayList<>();
		for(RepaymentPlan settings:dirtySettings){
			if(settings instanceof StandardRepaymentPlan){
				savedSettingsList.add(this.standardRepository.save((StandardRepaymentPlan)settings));
			}
			else if(settings instanceof FixedRepaymentPlan){
				savedSettingsList.add(this.fixedRepository.save((FixedRepaymentPlan)settings));
			}
		}
		return savedSettingsList;
	}
}
