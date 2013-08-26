package org.gsoft.openserv.web.loanprogram.duediligence.converter;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEventType;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceEventTypeRepository;
import org.gsoft.openserv.web.loanprogram.duediligence.model.DueDiligenceEventTypeModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DueDiligenceEventTypeModelToDueDiligenceEventType implements
		Converter<DueDiligenceEventTypeModel, DueDiligenceEventType> {
	@Resource
	private DueDiligenceEventTypeRepository dueDiligenceEventTypeRepository;
	
	@Override
	public DueDiligenceEventType convert(DueDiligenceEventTypeModel source) {
		Long id = source.getDueDiligenceEventTypeID();
		DueDiligenceEventType type = null;
		if(id == null || id < 0){
			type = new DueDiligenceEventType();
			type = dueDiligenceEventTypeRepository.save(type);
		}
		else{
			type = dueDiligenceEventTypeRepository.findOne(id);
		}
		type.setName(source.getName());
		type.setDescription(source.getDescription());
		return type;
	}

}
