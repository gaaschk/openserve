package org.gsoft.openserv.repositories.payment;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.payment.LateFee;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class LateFeeRepository extends BaseRepository<LateFee, Long>{
	@Resource
	private LateFeeSpringRepository lateFeeSpringRepository;

	@Override
	protected LateFeeSpringRepository getSpringRepository() {
		return lateFeeSpringRepository;
	}
	
	public LateFee findByBillingStatementID(Long billingStatementID){
		return this.getSpringRepository().findByBillingStatementID(billingStatementID);
	}
	
	public List<LateFee> findAllForLoan(Long loanID){
		return this.getSpringRepository().findAllByLoanID(loanID);
	}
} 

@Repository
interface LateFeeSpringRepository extends BaseSpringRepository<LateFee, Long>{
	@Query("SELECT lateFee FROM LateFee lateFee WHERE lateFee.billingStatementID = :billingStatementID and lateFee.cancelled = false")
	public LateFee findByBillingStatementID(@Param("billingStatementID") Long billingStatementID);

	@Query("SELECT lateFee FROM LateFee lateFee, BillingStatement bs WHERE lateFee.billingStatementID = bs.billingStatementID " +
			"AND bs.loanID = :loanID")
	public List<LateFee> findAllByLoanID(@Param("loanID") Long loanID);
}
