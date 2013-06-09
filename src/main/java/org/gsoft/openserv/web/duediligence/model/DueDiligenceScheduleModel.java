package org.gsoft.openserv.web.duediligence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DueDiligenceScheduleModel implements Serializable {
	private static final long serialVersionUID = -8673146955293220827L;

	private Long dueDiligenceScheduleID;
	private Date effectiveDate;
	private Date endDate;
	private List<DueDiligenceScheduleEventModel> events;

	public Long getDueDiligenceScheduleID() {
		return dueDiligenceScheduleID;
	}
	public void setDueDiligenceScheduleID(Long dueDiligenceScheduleID) {
		this.dueDiligenceScheduleID = dueDiligenceScheduleID;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<DueDiligenceScheduleEventModel> getEvents() {
		return events;
	}
	public void setEvents(List<DueDiligenceScheduleEventModel> events) {
		this.events = events;
	}
}
