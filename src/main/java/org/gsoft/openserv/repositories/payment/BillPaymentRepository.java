package org.gsoft.openserv.repositories.payment;

import org.gsoft.openserv.domain.payment.BillPayment;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillPaymentRepository extends BaseRepository<BillPayment, Long>{

}
