package org.gsoft.openserv.repositories.payment;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class LoanPaymentRepository extends BaseRepository<LoanPayment, Long>{
	@Resource
	private LoanPaymentSpringRepository loanPaymentSpringRepository;

	@Override
	protected BaseSpringRepository<LoanPayment, Long> getSpringRepository() {
		return loanPaymentSpringRepository;
	}
	
	
	public List<LoanPayment> findAllLoanPaymentsEffectiveOnOrAfter(Date effectiveDate){
		return this.loanPaymentSpringRepository.findAllLoanPaymentsEffectiveOnOrAfter(effectiveDate);
	}
}

@Repository
interface LoanPaymentSpringRepository extends BaseSpringRepository<LoanPayment, Long>{
	
	@Query("select lp from LoanPayment lp where lp.payment.effectiveDate >= :effectiveDate order by lp.payment.effectiveDate desc, lp.payment.postDate desc")
	public List<LoanPayment> findAllLoanPaymentsEffectiveOnOrAfter(@Param("effectiveDate") Date effectiveDate);
}
