package org.gsoft.openserv.repositories.payment;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository extends BaseRepository<Payment, Long>{
	@Resource
	private PaymentSpringRepository paymentSpringRepository;

	@Override
	protected BaseSpringRepository<Payment, Long> getSpringRepository() {
		return paymentSpringRepository;
	}

	public List<Payment> findAllPaymentsByBorrowerPersonID(Long borrowerPersonID){
		return this.paymentSpringRepository.findAllPaymentsByBorrowerPersonID(borrowerPersonID);
	}
}

@Repository
interface PaymentSpringRepository extends BaseSpringRepository<Payment, Long>{
	@Query("select p from Payment p where p.borrowerPersonID = :borrowerPersonID")
	public List<Payment> findAllPaymentsByBorrowerPersonID(@Param("borrowerPersonID") Long borrowerPersonID);
}
