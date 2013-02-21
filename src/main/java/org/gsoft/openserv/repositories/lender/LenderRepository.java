package org.gsoft.openserv.repositories.lender;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.lender.Lender;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LenderRepository extends BaseRepository<Lender, Long> {
	@Resource
	private LenderSpringRepository lenderSpringRepository;
	
	@Override
	protected LenderSpringRepository getSpringRepository() {
		return lenderSpringRepository;
	}

}

interface LenderSpringRepository extends BaseSpringRepository<Lender, Long>{
	
}