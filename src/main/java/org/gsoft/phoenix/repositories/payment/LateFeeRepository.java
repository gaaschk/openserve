package org.gsoft.phoenix.repositories.payment;

import org.gsoft.phoenix.domain.payment.LateFee;
import org.gsoft.phoenix.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LateFeeRepository extends BaseRepository<LateFee, Long>{
	
}
