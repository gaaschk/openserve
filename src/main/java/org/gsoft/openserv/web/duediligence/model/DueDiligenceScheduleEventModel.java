package org.gsoft.openserv.web.duediligence.model;

import java.io.Serializable;

import org.springframework.format.annotation.NumberFormat;

public class DueDiligenceScheduleEventModel implements Serializable {
	private static final long serialVersionUID = -2085521279517077929L;

	private Long dueDiligenceEventID;
	private Long dueDiligenceEventTypeId;
	@NumberFormat
	private Integer minDelqDays;
	@NumberFormat
	private Integer maxDelqDays;
	@NumberFormat
	private Integer defaultDelqDays;
	
	public Long getDueDiligenceEventID() {
		return dueDiligenceEventID;
	}
	public void setDueDiligenceEventID(Long dueDiligenceEventID) {
		this.dueDiligenceEventID = dueDiligenceEventID;
	}
	public Long getDueDiligenceEventTypeId() {
		return dueDiligenceEventTypeId;
	}
	public void setDueDiligenceEventTypeId(Long dueDiligenceEventTypeId) {
		this.dueDiligenceEventTypeId = dueDiligenceEventTypeId;
	}
	public Integer getMinDelqDays() {
		return minDelqDays;
	}
	public void setMinDelqDays(Integer minDelqDays) {
		this.minDelqDays = minDelqDays;
	}
	public Integer getMaxDelqDays() {
		return maxDelqDays;
	}
	public void setMaxDelqDays(Integer maxDelqDays) {
		this.maxDelqDays = maxDelqDays;
	}
	public Integer getDefaultDelqDays() {
		return defaultDelqDays;
	}
	public void setDefaultDelqDays(Integer defaultDelqDays) {
		this.defaultDelqDays = defaultDelqDays;
	}
}
