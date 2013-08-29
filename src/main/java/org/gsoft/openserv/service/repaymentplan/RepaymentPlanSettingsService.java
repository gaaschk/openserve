package org.gsoft.openserv.service.repaymentplan;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.repayment.RepaymentPlanSettings;
import org.gsoft.openserv.repositories.repayment.RepaymentPlanSettingsRepository;
import org.gsoft.openserv.rulesengine.annotation.RunRulesEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class RepaymentPlanSettingsService {
	@Resource
	private RepaymentPlanSettingsRepository repository;
	
	@Transactional
	@RunRulesEngine
	public List<RepaymentPlanSettings> saveRepaymentPlanSettings(List<RepaymentPlanSettings> dirtySettings){
		List<RepaymentPlanSettings> savedSettingsList = new ArrayList<>();
		for(RepaymentPlanSettings settings:dirtySettings){
			savedSettingsList.add(repository.save(settings));
		}
		return savedSettingsList;
	}
}
