package org.gsoft.phoenix.repositories.payment;

import java.util.List;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.payment.Payment;
import org.gsoft.phoenix.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long>{
	@Query("select p from Payment p where p.borrowerPersonID = :borrowerPersonID")
	public List<Payment> findAllPaymentsByBorrowerPersonID(@Param("borrowerPersonID") Long borrowerPersonID);
}
