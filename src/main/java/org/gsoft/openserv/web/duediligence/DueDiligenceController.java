package org.gsoft.openserv.web.duediligence;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEventType;
import org.gsoft.openserv.domain.duediligence.DueDiligenceSchedule;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceEventTypeRepository;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceScheduleRepository;
import org.gsoft.openserv.repositories.loan.LoanProgramRepository;
import org.gsoft.openserv.service.duediligence.ManageDueDiligenceService;
import org.gsoft.openserv.web.duediligence.model.ManageDueDiligenceEventTypesModel;
import org.gsoft.openserv.web.duediligence.model.ManageDueDiligenceSchedulesModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("duediligence")
public class DueDiligenceController {
	@Resource
	private DueDiligenceEventTypeRepository dueDiligenceEventTypeRepo;
	@Resource
	private DueDiligenceScheduleRepository dueDiligenceScheduleRepo;
	@Resource
	private ManageDueDiligenceService dueDiligenceService;
	@Resource
	private LoanProgramRepository loanProgramRepo;
	
	public ManageDueDiligenceEventTypesModel loadAllDueDiligenceEventTypes(){
		List<DueDiligenceEventType> allTypes = dueDiligenceEventTypeRepo.findAll();
		ManageDueDiligenceEventTypesModel model = new ManageDueDiligenceEventTypesModel();
		model.setAllDueDiligenceEventTypes(allTypes);
		return model;
	}
	
	public void addNewDueDiligenceType(ManageDueDiligenceEventTypesModel model){
		model.getAllDueDiligenceEventTypes().add(new DueDiligenceEventType());
	}
	
	public void save(ManageDueDiligenceEventTypesModel model){
		dueDiligenceService.saveDueDiligenceTypes(model.getAllDueDiligenceEventTypes());
	}
	
	public ManageDueDiligenceSchedulesModel loadAllDueDiligenceSchedules(){
		ManageDueDiligenceSchedulesModel model = new ManageDueDiligenceSchedulesModel();
		model.setAllEventTypes(dueDiligenceEventTypeRepo.findAll());
		model.setAllLoanPrograms(loanProgramRepo.findAll());
		model.setSchedules(new ArrayList<DueDiligenceSchedule>());
		for(LoanProgram program:model.getAllLoanPrograms()){
			model.getSchedules().addAll(dueDiligenceScheduleRepo.findAllByLoanProgramId(program.getLoanProgramID()));
		}
		return model;
	}
	
	public void addNewDueDiligenceSchedule(ManageDueDiligenceSchedulesModel model){
		model.getSchedules().add(new DueDiligenceSchedule());
	}
	
	public void setLoanProgram(ManageDueDiligenceSchedulesModel model){
		System.out.println("Loan Program Set");
	}
	
	@RequestMapping(value="/schedules.do", method=RequestMethod.POST)
	public ManageDueDiligenceSchedulesModel loadSchedules(@RequestParam("loanprogramid") String loanProgramID, ManageDueDiligenceSchedulesModel mv){
		Long lpId = Long.parseLong(loanProgramID);
		mv.setSelectedLoanProgram(loanProgramRepo.findOne(lpId));
		return mv;
	}
}
