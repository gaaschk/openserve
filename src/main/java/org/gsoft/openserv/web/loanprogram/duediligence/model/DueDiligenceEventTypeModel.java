package org.gsoft.openserv.web.loanprogram.duediligence.model;

public class DueDiligenceEventTypeModel {
	private Long dueDiligenceEventTypeID;
	private String name;
	private String description;
	
	public Long getDueDiligenceEventTypeID() {
		return dueDiligenceEventTypeID;
	}
	public void setDueDiligenceEventTypeID(Long dueDiligenceEventTypeID) {
		this.dueDiligenceEventTypeID = dueDiligenceEventTypeID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
