package org.gsoft.phoenix.repositories.payment;

import org.gsoft.phoenix.domain.payment.Payment;
import org.gsoft.phoenix.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long>{
	
}
