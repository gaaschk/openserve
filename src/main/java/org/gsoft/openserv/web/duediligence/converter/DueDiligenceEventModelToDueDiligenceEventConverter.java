package org.gsoft.openserv.web.duediligence.converter;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEvent;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceEventRepository;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceEventTypeRepository;
import org.gsoft.openserv.web.duediligence.model.DueDiligenceScheduleEventModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DueDiligenceEventModelToDueDiligenceEventConverter  implements Converter<DueDiligenceScheduleEventModel, DueDiligenceEvent>{
	@Resource
	private DueDiligenceEventRepository dueDiligenceEventRepo;
	@Resource
	private DueDiligenceEventTypeRepository dueDiligenceEventTypeRepo;
	
	@Override
	public DueDiligenceEvent convert(DueDiligenceScheduleEventModel source) {
		DueDiligenceEvent event = null;
		if(source.getDueDiligenceEventID()!=null){
			event = dueDiligenceEventRepo.findOne(source.getDueDiligenceEventID());
		}
		else{
			event = new DueDiligenceEvent();
		}
		event.setDefaultDelqDays(source.getDefaultDelqDays());
		if(source.getDueDiligenceEventTypeId() != null){
			event.setDueDiligenceEventType(dueDiligenceEventTypeRepo.findOne(source.getDueDiligenceEventTypeId()));
		}
		event.setMaxDelqDays(source.getMaxDelqDays());
		event.setMinDelqDays(source.getMinDelqDays());
		return event;
	}

}