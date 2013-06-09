package org.gsoft.openserv.web.duediligence.model;

import java.io.Serializable;
import java.util.List;

public class DueDiligenceSchedulesModel implements Serializable{
	private static final long serialVersionUID = 2042781250471994377L;

	private Long loanProgramId 						= null;
	private List<DueDiligenceScheduleModel> schedules 	= null;

	public Long getLoanProgramId() {
		return loanProgramId;
	}
	public void setLoanProgramId(Long loanProgramId) {
		this.loanProgramId = loanProgramId;
	}
	public List<DueDiligenceScheduleModel> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<DueDiligenceScheduleModel> schedules) {
		this.schedules = schedules;
	}
}
