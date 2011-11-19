package org.gsoft.phoenix.buslogic.system;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.system.SystemSettings;
import org.gsoft.phoenix.repositories.system.SystemSettingsRepository;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

@Component
public class SystemSettingsLogic {
	private static final long CURRENT_SETTINGS_ID = 10;

	@Resource
	private SystemSettingsRepository systemSettingsRepository;
	
	public Date getCurrentSystemDate(){
		SystemSettings settings = systemSettingsRepository.findOne(10L);
		return this.getAdjustedSystemDate(settings);
	}
		
	public Date setSystemDateDeltaByCalendarUnit(int calendarUnit, int amount){
		SystemSettings settings = systemSettingsRepository.findOne(CURRENT_SETTINGS_ID);
		settings.setDaysDelta(0);
		this.adjustSystemDateByCalendarUnit(settings, calendarUnit, amount);
		return this.getAdjustedSystemDate(settings);
	}
	
	public Date adjustSystemDateByCalendarUnit(int calendarUnit, int amount){
		SystemSettings settings = systemSettingsRepository.findOne(CURRENT_SETTINGS_ID);
		this.adjustSystemDateByCalendarUnit(settings, calendarUnit, amount);
		return this.getAdjustedSystemDate(settings);
	}
	
	private Date getAdjustedSystemDate(SystemSettings systemSettings){
		Calendar dateCal = Calendar.getInstance();
		dateCal.add(Calendar.DAY_OF_YEAR, systemSettings.getDaysDelta());
		return dateCal.getTime();
	}
	
	private void adjustSystemDateByCalendarUnit(SystemSettings systemSettings, int calendarUnit, int amount){
		/*we have to pull out the "from" date first and cache it because the between method will only report full days*/
		Date cachedUnadjustedDate = this.getUnadjustedSystemDate();
		Calendar adjustedDate = Calendar.getInstance();
		adjustedDate.setTime(this.getCurrentSystemDate());
		adjustedDate.add(calendarUnit, amount);
		/*have to add a day here because the daysBetween method is exclusive on the end boundary*/
		Days days = Days.daysBetween(new DateTime(cachedUnadjustedDate.getTime()), new DateTime(adjustedDate.getTimeInMillis()));
		systemSettings.setDaysDelta(days.getDays());
	}
	
	private Date getUnadjustedSystemDate(){
		return new Date();
	}
}
