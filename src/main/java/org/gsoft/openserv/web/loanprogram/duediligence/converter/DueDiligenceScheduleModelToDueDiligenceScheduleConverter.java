package org.gsoft.openserv.web.loanprogram.duediligence.converter;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEvent;
import org.gsoft.openserv.domain.duediligence.DueDiligenceSchedule;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceEventRepository;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceScheduleRepository;
import org.gsoft.openserv.repositories.loan.LoanProgramRepository;
import org.gsoft.openserv.web.loanprogram.duediligence.model.DueDiligenceScheduleEventModel;
import org.gsoft.openserv.web.loanprogram.duediligence.model.DueDiligenceScheduleModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DueDiligenceScheduleModelToDueDiligenceScheduleConverter implements Converter<DueDiligenceScheduleModel, DueDiligenceSchedule> {
	@Resource
	private DueDiligenceScheduleRepository dueDiligenceScheduleRepo;
	@Resource
	private DueDiligenceEventRepository dueDiligenceEventRepo;
	@Resource
	private LoanProgramRepository loanProgramRepository;
	@Resource
	private ConversionService conversionService;
	
	@Override
	public DueDiligenceSchedule convert(DueDiligenceScheduleModel source) {
		DueDiligenceSchedule schedule = null;
		if(source.getDueDiligenceScheduleID() == null || source.getDueDiligenceScheduleID() < 0){
			schedule = new DueDiligenceSchedule();
			schedule = dueDiligenceScheduleRepo.save(schedule);
			schedule.setLoanProgram(loanProgramRepository.findOne(source.getLoanProgramID()));
		}
		else{
			schedule = this.dueDiligenceScheduleRepo.findOne(source.getDueDiligenceScheduleID());
		}
		schedule.setEffectiveDate(source.getEffectiveDate());
		if(schedule.getEvents() == null){
			schedule.setEvents(new ArrayList<DueDiligenceEvent>());
		}
		if(source.getEvents() != null){
			for(DueDiligenceScheduleEventModel eventModel:source.getEvents()){
				DueDiligenceEvent event = conversionService.convert(eventModel, DueDiligenceEvent.class);
				event.setDueDiligenceSchedule(schedule);
				dueDiligenceEventRepo.save(event);
				schedule.getEvents().add(event);
			}
		}
		return schedule;
	}

}
