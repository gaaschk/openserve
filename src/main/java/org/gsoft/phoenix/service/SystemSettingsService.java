package org.gsoft.phoenix.service;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.system.SystemSettingsLogic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemSettingsService {
	@Resource
	private SystemSettingsLogic systemSettingsLogic;
	
	public Date getCurrentSystemDate(){
		return systemSettingsLogic.getCurrentSystemDate();
	}
	
	public Date setSystemDateDeltaByCalendarUnit(int unit, int amount){
		return this.systemSettingsLogic.setSystemDateDeltaByCalendarUnit(unit, amount);
	}
	
	public Date adjustSystemDateByCalendarUnit(int unit, int amount){
		return this.systemSettingsLogic.adjustSystemDateByCalendarUnit(unit, amount);
	}
}
