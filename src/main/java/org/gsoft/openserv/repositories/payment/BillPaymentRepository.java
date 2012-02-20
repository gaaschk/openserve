package org.gsoft.openserv.repositories.payment;

import org.gsoft.openserv.domain.payment.BillPayment;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillPaymentRepository extends BaseSpringRepository<BillPayment, Long>{

}
