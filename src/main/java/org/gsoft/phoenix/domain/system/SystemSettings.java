package org.gsoft.phoenix.domain.system;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

@Entity
public class SystemSettings extends PhoenixDomainObject{
	private static final long serialVersionUID = 4418309716452336867L;
	private Long systemSettingsID;
	private Integer daysDelta;
	private Boolean triggerBatch;

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

	public Boolean getTriggerBatch() {
		return triggerBatch;
	}

	public void setTriggerBatch(Boolean triggerBatch) {
		this.triggerBatch = triggerBatch;
	}

	@Override
	@Transient
	public Long getID() {
		return this.getSystemSettingsID();
	}
}
