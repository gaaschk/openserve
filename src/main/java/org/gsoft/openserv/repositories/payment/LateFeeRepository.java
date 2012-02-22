package org.gsoft.openserv.repositories.payment;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.payment.LateFee;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LateFeeRepository extends BaseRepository<LateFee, Long>{
	@Resource
	private LateFeeSpringRepository lateFeeSpringRepository;

	@Override
	protected BaseSpringRepository<LateFee, Long> getSpringRepository() {
		return lateFeeSpringRepository;
	}
} 

@Repository
interface LateFeeSpringRepository extends BaseSpringRepository<LateFee, Long>{
	
}
