package org.gsoft.openserv.service.duediligence;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEventType;
import org.gsoft.openserv.domain.duediligence.DueDiligenceSchedule;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceEventTypeRepository;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceScheduleRepository;
import org.gsoft.openserv.rulesengine.annotation.RunRulesEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class ManageDueDiligenceService {
	@Resource
	private DueDiligenceEventTypeRepository dueDiligenceEventTypeRepository;
	@Resource
	private DueDiligenceScheduleRepository dueDiligenceScheduleRepository;
	
	@Transactional
	@RunRulesEngine
	public List<DueDiligenceEventType> saveDueDiligenceTypes(List<DueDiligenceEventType> types){
		List<DueDiligenceEventType> savedTypes = new ArrayList<>();
		for(DueDiligenceEventType type:types){
			savedTypes.add(dueDiligenceEventTypeRepository.save(type));
		}
		return savedTypes;
	}
	
	@Transactional
	@RunRulesEngine
	public List<DueDiligenceSchedule> saveDueDiligenceSchedule(List<DueDiligenceSchedule> schedules){
		List<DueDiligenceSchedule> savedSchedules = new ArrayList<>();
		for(DueDiligenceSchedule schedule:schedules){
			savedSchedules.add(dueDiligenceScheduleRepository.save(schedule));
		}
		return savedSchedules;
	}
}
