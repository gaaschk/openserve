package org.gsoft.openserv.repositories.payment;

import org.gsoft.openserv.domain.payment.LateFee;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LateFeeRepository extends BaseRepository<LateFee, Long>{
	
}
