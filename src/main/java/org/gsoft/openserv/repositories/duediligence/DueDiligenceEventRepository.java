package org.gsoft.openserv.repositories.duediligence;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEvent;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DueDiligenceEventRepository extends BaseRepository<DueDiligenceEvent,Long> {
	@Resource
	private DueDiligenceEventSpringRepository springRepository;  
	
	@Override
	protected DueDiligenceEventSpringRepository getSpringRepository() {
		return springRepository;
	}
}

@Repository
interface DueDiligenceEventSpringRepository extends BaseSpringRepository<DueDiligenceEvent, Long>{
	
}