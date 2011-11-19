package org.gsoft.phoenix.domain.system;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

@Entity
public class SystemSettings implements PhoenixDomainObject{
	private static final long serialVersionUID = 4418309716452336867L;
	private Long systemSettingsID;
	private Integer daysDelta;

	@Id
	public Long getSystemSettingsID() {
		return systemSettingsID;
	}

	public void setSystemSettingsID(Long systemSettingsID) {
		this.systemSettingsID = systemSettingsID;
	}

	public Integer getDaysDelta() {
		return daysDelta;
	}

	public void setDaysDelta(Integer daysDelta) {
		this.daysDelta = daysDelta;
	}
	
}
