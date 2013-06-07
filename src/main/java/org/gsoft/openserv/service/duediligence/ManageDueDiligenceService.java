package org.gsoft.openserv.service.duediligence;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEventType;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceEventTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class ManageDueDiligenceService {
	@Resource
	private DueDiligenceEventTypeRepository dueDiligenceEventTypeRepository;
	
	public void saveDueDiligenceTypes(List<DueDiligenceEventType> types){
		for(DueDiligenceEventType type:types){
			dueDiligenceEventTypeRepository.save(type);
		}
	}
}
