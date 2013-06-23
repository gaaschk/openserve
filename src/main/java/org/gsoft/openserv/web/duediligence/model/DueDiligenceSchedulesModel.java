package org.gsoft.openserv.web.duediligence.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;

public class DueDiligenceSchedulesModel implements Serializable{
	private static final long serialVersionUID = 2042781250471994377L;

	private Long loanProgramId 						= null;
	@NumberFormat
	private Long selectedScheduleId					= null;
	private List<DueDiligenceScheduleModel> schedules 	= null;

	public Long getLoanProgramId() {
		return loanProgramId;
	}
	public void setLoanProgramId(Long loanProgramId) {
		this.loanProgramId = loanProgramId;
	}
	public Long getSelectedScheduleId() {
		return selectedScheduleId;
	}
	public void setSelectedScheduleId(Long selectedScheduleId) {
		this.selectedScheduleId = selectedScheduleId;
	}
	public List<DueDiligenceScheduleModel> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<DueDiligenceScheduleModel> schedules) {
		this.schedules = schedules;
	}
}
