package org.gsoft.openserv.web.loanprogram.duediligence.converter;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEventType;
import org.gsoft.openserv.web.loanprogram.duediligence.model.DueDiligenceEventTypeModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DueDiligenceEventTypeToDueDiligenceEventTypeModel implements Converter<DueDiligenceEventType, DueDiligenceEventTypeModel>{

	@Override
	public DueDiligenceEventTypeModel convert(DueDiligenceEventType source) {
		DueDiligenceEventTypeModel model = new DueDiligenceEventTypeModel();
		model.setDueDiligenceEventTypeID(source.getDueDiligenceEventTypeID());
		model.setName(source.getName());
		model.setDescription(source.getDescription());
		return model;
	}

}
