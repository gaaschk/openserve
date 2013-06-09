package org.gsoft.openserv.web.duediligence.model;

import java.io.Serializable;
import java.util.List;

import org.gsoft.openserv.domain.duediligence.DueDiligenceSchedule;

public class DueDiligenceScheduleModel implements Serializable{
	private Long loanProgramId 						= null;
	private List<DueDiligenceSchedule> schedules 	= null;

	public Long getLoanProgramId() {
		return loanProgramId;
	}
	public void setLoanProgramId(Long loanProgramId) {
		this.loanProgramId = loanProgramId;
	}
	public List<DueDiligenceSchedule> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<DueDiligenceSchedule> schedules) {
		this.schedules = schedules;
	}
}
