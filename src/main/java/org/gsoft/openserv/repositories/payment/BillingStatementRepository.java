package org.gsoft.openserv.repositories.payment;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.payment.billing.BillingStatement;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingStatementRepository extends BaseRepository<BillingStatement, Long>{
	
	@Query("select bs from BillingStatement bs where bs.loanID = :loanID and bs.dueDate = " +
			"(select max(bs2.dueDate) from BillingStatement bs2 where bs2.loanID = :loanID)")
	BillingStatement findMostRecentBillingStatementForLoan(@Param("loanID") Long loanID);

	@Query("select bs from BillingStatement bs where bs.loanID = :loanID order by bs.dueDate desc")
	List<BillingStatement> findAllBillsForLoan(@Param("loanID") Long loanID);
	
	@Query("select bs from BillingStatement bs where bs.loanID = :loanID and bs.dueDate <= :asOfDate order by bs.dueDate desc")
	List<BillingStatement> findAllBillsForLoanOnOrBefore(@Param("loanID") Long loanID, @Param("asOfDate") Date asOfDate);
}