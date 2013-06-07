package org.gsoft.openserv.web.duediligence.model;

import java.io.Serializable;
import java.util.List;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEventType;

public class ManageDueDiligenceEventTypesModel implements Serializable{
	private static final long serialVersionUID = 8823037003053542938L;

	private List<DueDiligenceEventType> allDueDiligenceEventTypes;

	public List<DueDiligenceEventType> getAllDueDiligenceEventTypes() {
		return allDueDiligenceEventTypes;
	}

	public void setAllDueDiligenceEventTypes(List<DueDiligenceEventType> allDueDiligenceEventTypes) {
		this.allDueDiligenceEventTypes = allDueDiligenceEventTypes;
	}
	
}
