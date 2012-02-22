package org.gsoft.openserv.repositories.payment;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.payment.BillPayment;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BillPaymentRepository extends BaseRepository<BillPayment, Long>{
	@Resource
	private BillPaymentSpringRepository billPaymentSpringRepository;

	@Override
	protected BaseSpringRepository<BillPayment, Long> getSpringRepository() {
		return billPaymentSpringRepository;
	}
}

@Repository
interface BillPaymentSpringRepository extends BaseSpringRepository<BillPayment, Long>{

}
