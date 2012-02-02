package org.gsoft.openserv.repositories.payment;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPaymentRepository extends BaseRepository<LoanPayment, Long>{
	
	@Query("select lp from LoanPayment lp where lp.payment.effectiveDate >= :effectiveDate order by lp.payment.effectiveDate desc, lp.payment.postDate desc")
	public List<LoanPayment> findAllLoanPaymentsEffectiveOnOrAfter(@Param("effectiveDate") Date effectiveDate);
}