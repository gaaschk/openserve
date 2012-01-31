package org.gsoft.phoenix.repositories.payment;

import org.gsoft.phoenix.domain.payment.LoanPayment;
import org.gsoft.phoenix.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPaymentRepository extends BaseRepository<LoanPayment, Long>{

}
