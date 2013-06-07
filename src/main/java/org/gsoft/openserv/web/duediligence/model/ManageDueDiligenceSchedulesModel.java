package org.gsoft.openserv.web.duediligence.model;

import java.io.Serializable;
import java.util.List;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEventType;
import org.gsoft.openserv.domain.duediligence.DueDiligenceSchedule;
import org.gsoft.openserv.domain.loan.LoanProgram;

public class ManageDueDiligenceSchedulesModel implements Serializable {
	private static final long serialVersionUID = 7542664563507400359L;
	
	//for comboboxes
	private List<LoanProgram> allLoanPrograms = null;
	private LoanProgram selectedLoanProgram = null;

	private List<DueDiligenceEventType> allEventTypes = null;
	
	private List<DueDiligenceSchedule> schedules = null;
	
	public List<DueDiligenceSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<DueDiligenceSchedule> schedules) {
		this.schedules = schedules;
	}

	public List<DueDiligenceEventType> getAllEventTypes() {
		return allEventTypes;
	}

	public void setAllEventTypes(List<DueDiligenceEventType> allEventTypes) {
		this.allEventTypes = allEventTypes;
	}

	public List<LoanProgram> getAllLoanPrograms() {
		return allLoanPrograms;
	}

	public void setAllLoanPrograms(List<LoanProgram> allLoanPrograms) {
		this.allLoanPrograms = allLoanPrograms;
	}

	public LoanProgram getSelectedLoanProgram() {
		return selectedLoanProgram;
	}

	public void setSelectedLoanProgram(LoanProgram selectedLoanProgram) {
		this.selectedLoanProgram = selectedLoanProgram;
	}
}
