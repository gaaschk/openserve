package org.gsoft.openserv.repositories.payment;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.payment.LateFee;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LateFeeRepository extends BaseRepository<LateFee, Long>{
	@Query("SELECT lateFee FROM LateFee lateFee WHERE lateFee.billingStatementID = :billingStatementID and lateFee.cancelled = false")
	public LateFee findByBillingStatementID(@Param("billingStatementID") Long billingStatementID);

	@Query("SELECT lateFee FROM LateFee lateFee, BillingStatement bs WHERE lateFee.billingStatementID = bs.billingStatementID " +
			"AND bs.loanID = :loanID")
	public List<LateFee> findAllByLoanID(@Param("loanID") Long loanID);

	@Query("SELECT lateFee FROM LateFee lateFee, BillingStatement bs WHERE lateFee.billingStatementID = bs.billingStatementID " +
			"AND bs.loanID = :loanID and lateFee.effectiveDate <= :assessedDate")
	public List<LateFee> findAllByLoanIDAssessedOnOrBefore(@Param("loanID") Long loanID, @Param("assessedDate") Date assessedDate);
}