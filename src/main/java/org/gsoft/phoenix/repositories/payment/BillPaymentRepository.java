package org.gsoft.phoenix.repositories.payment;

import org.gsoft.phoenix.domain.payment.BillPayment;
import org.gsoft.phoenix.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillPaymentRepository extends BaseRepository<BillPayment, Long>{

}
