package org.gsoft.openserv.service;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class SystemSettingsService {
	@Resource
	private SystemSettingsLogic systemSettingsLogic;
	
	public Date getCurrentSystemDate(){
		return systemSettingsLogic.getCurrentSystemDate();
	}
	
	@Transactional
	public Date setSystemDateDeltaByCalendarUnit(int unit, int amount){
		return this.systemSettingsLogic.setSystemDateDeltaByCalendarUnit(unit, amount);
	}
	
	@Transactional
	public Date adjustSystemDateByCalendarUnit(int unit, int amount){
		return this.systemSettingsLogic.adjustSystemDateByCalendarUnit(unit, amount);
	}
	
	public Boolean isBatchTriggered(){
		return this.systemSettingsLogic.getCurrentSystemSettings().getShouldTriggerBatch();
	}
	
	@Transactional
	public void clearBatchTrigger(){
		this.systemSettingsLogic.getCurrentSystemSettings().setShouldTriggerBatch(false);
	}

	@Transactional
	public void setBatchTrigger(){
		this.systemSettingsLogic.getCurrentSystemSettings().setShouldTriggerBatch(true);
	}
}
