package org.gsoft.openserv.repositories.duediligence;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.duediligence.DueDiligenceEventType;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DueDiligenceEventTypeRepository extends BaseRepository<DueDiligenceEventType, Long> {
	@Resource
	private DueDiligenceEventTypeSpringRepository springRepository;
	
	@Override
	protected DueDiligenceEventTypeSpringRepository getSpringRepository() {
		return springRepository;
	}

}

@Repository
interface DueDiligenceEventTypeSpringRepository extends BaseSpringRepository<DueDiligenceEventType, Long>{
	
}
