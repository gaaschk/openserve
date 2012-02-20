package org.gsoft.openserv.repositories.payment;

import org.gsoft.openserv.domain.payment.LateFee;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LateFeeRepository extends BaseSpringRepository<LateFee, Long>{
	
}
