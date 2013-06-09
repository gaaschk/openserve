package org.gsoft.openserv.web.duediligence.converter;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEvent;
import org.gsoft.openserv.web.duediligence.model.DueDiligenceScheduleEventModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DueDiligenceEventToDueDiligenceEventModelConverter implements Converter<DueDiligenceEvent, DueDiligenceScheduleEventModel>{

	@Override
	public DueDiligenceScheduleEventModel convert(DueDiligenceEvent source) {
		DueDiligenceScheduleEventModel eventModel = new DueDiligenceScheduleEventModel();
		eventModel.setMinDelqDays(source.getMinDelqDays());
		eventModel.setMaxDelqDays(source.getMaxDelqDays());
		eventModel.setDefaultDelqDays(source.getDefaultDelqDays());
		eventModel.setDueDiligenceEventTypeId(source.getDueDiligenceEventType().getDueDiligenceEventTypeID());
		eventModel.setDueDiligenceEventID(source.getDueDiligenceEventID());
		return eventModel;
	}

}
