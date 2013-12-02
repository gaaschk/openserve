package org.gsoft.openserv.web.loanprogram.duediligence.model;

import java.io.Serializable;
import java.util.List;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEventType;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.springframework.format.annotation.NumberFormat;

public class ManageDueDiligenceSchedulesModel implements Serializable {
	private static final long serialVersionUID = 7542664563507400359L;
	
	//for comboboxes
	private List<LoanProgram> allLoanPrograms = null;
	
	@NumberFormat
	private Long selectedLoanProgramID = null;
	
	/**
	 * this is a junk property. for some reason spring webflow is insisting on 
	 * setting this property on my model
	 */
	private String execution = null;

	private List<DueDiligenceSchedulesModel> scheduleModels = null;
	
	private List<DueDiligenceEventType> allEventTypes = null;
	
	
	public List<DueDiligenceSchedulesModel> getScheduleModels() {
		return scheduleModels;
	}

	public void setScheduleModels(List<DueDiligenceSchedulesModel> scheduleModels) {
		this.scheduleModels = scheduleModels;
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

	public Long getSelectedLoanProgramID() {
		return selectedLoanProgramID;
	}

	public void setSelectedLoanProgramID(Long selectedLoanProgramID) {
		this.selectedLoanProgramID = selectedLoanProgramID;
	}

	public String getExecution() {
		return execution;
	}

	public void setExecution(String execution) {
		this.execution = execution;
	}
}
