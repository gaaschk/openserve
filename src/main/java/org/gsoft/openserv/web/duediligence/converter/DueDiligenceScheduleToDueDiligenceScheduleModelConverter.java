package org.gsoft.openserv.web.duediligence.converter;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEvent;
import org.gsoft.openserv.domain.duediligence.DueDiligenceSchedule;
import org.gsoft.openserv.web.duediligence.model.DueDiligenceScheduleEventModel;
import org.gsoft.openserv.web.duediligence.model.DueDiligenceScheduleModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DueDiligenceScheduleToDueDiligenceScheduleModelConverter implements
		Converter<DueDiligenceSchedule, DueDiligenceScheduleModel> {
	@Resource
	private ConversionService conversionService;

	@Override
	public DueDiligenceScheduleModel convert(DueDiligenceSchedule source) {
		DueDiligenceScheduleModel model = new DueDiligenceScheduleModel();
		model.setDueDiligenceScheduleID(source.getDueDiligenceScheduleID());
		model.setEffectiveDate(source.getEffectiveDate());
		model.setEndDate(source.getEndDate());
		model.setEvents(new ArrayList<DueDiligenceScheduleEventModel>());
		for(DueDiligenceEvent event:source.getEvents()){
			model.getEvents().add(conversionService.convert(event, DueDiligenceScheduleEventModel.class));
		}
		return model;
	}

}
