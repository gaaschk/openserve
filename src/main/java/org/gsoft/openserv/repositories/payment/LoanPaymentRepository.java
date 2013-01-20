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
	protected LoanPaymentSpringRepository getSpringRepository() {
		return loanPaymentSpringRepository;
	}
	
	public LoanPayment findMostRecentLoanPayment(Long loanID){
		List<LoanPayment> allPayments = this.getSpringRepository().findAllLoanPayments(loanID);
		LoanPayment payment = null;
		if(allPayments != null && allPayments.size()>0)
			payment = allPayments.get(allPayments.size()-1);
		return payment;
	}
	
	public List<LoanPayment> findAllLoanPayments(Long loanID){
		return this.loanPaymentSpringRepository.findAllLoanPayments(loanID);
	}

	public List<LoanPayment> findAllLoanPaymentsEffectiveOnOrAfter(Date effectiveDate){
		return this.loanPaymentSpringRepository.findAllLoanPaymentsEffectiveOnOrAfter(effectiveDate);
	}

	public List<LoanPayment> findAllLoanPaymentsEffectiveOnOrBefore(Long loanID, Date effectiveDate){
		return this.loanPaymentSpringRepository.findAllLoanPaymentsForLoanEffectiveOnOrBefore(loanID, effectiveDate);
	}
}

@Repository
interface LoanPaymentSpringRepository extends BaseSpringRepository<LoanPayment, Long>{
	@Query("select lp from LoanPayment lp where lp.loanID= :loanID order by lp.payment.effectiveDate asc, lp.payment.postDate asc")
	public List<LoanPayment> findAllLoanPayments(@Param("loanID") Long loanID);

	@Query("select lp from LoanPayment lp where lp.payment.effectiveDate >= :effectiveDate order by lp.payment.effectiveDate desc, lp.payment.postDate desc")
	public List<LoanPayment> findAllLoanPaymentsEffectiveOnOrAfter(@Param("effectiveDate") Date effectiveDate);

	@Query("select lp from LoanPayment lp where lp.loanID = :loanID AND lp.payment.effectiveDate <= :effectiveDate order by lp.payment.effectiveDate asc, lp.payment.postDate asc")
	public List<LoanPayment> findAllLoanPaymentsForLoanEffectiveOnOrBefore(@Param("loanID") Long loanID, @Param("effectiveDate") Date effectiveDate);
}
