package org.gsoft.openserv.repositories.lender;

import org.gsoft.openserv.domain.lender.Lender;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LenderRepository extends BaseRepository<Lender, Long>{
	
}