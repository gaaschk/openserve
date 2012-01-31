package org.gsoft.phoenix.repositories.payment;

import java.util.Date;
import java.util.List;

import org.gsoft.phoenix.domain.payment.BillingStatement;
import org.gsoft.phoenix.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingStatementRepository extends BaseRepository<BillingStatement, Long>{
	
	@Query("select bs from BillingStatement bs where bs.loanID = :loanID and bs.dueDate = " +
			"(select max(bs2.dueDate) from BillingStatement bs2 where bs2.loanID = :loanID)")
	public BillingStatement findMostRecentBillingStatementForLoan(@Param("loanID") Long loanID);

	@Query("select bs from BillingStatement bs where bs.loanID = :loanID and " + 
			"bs.dueDate <= :checkDate and (bs.paidAmount is null or bs.paidAmount < bs.minimumRequiredPayment) order by bs.dueDate asc")
	public List<BillingStatement> findAllUnpaidBillsForLoanWithDueDateOnOrBefore(@Param("loanID") Long loanID, @Param("checkDate") Date checkDate);

	@Query("select bs from BillingStatement bs where bs.loanID = :loanID and bs.dueDate = " +
			"(select max(bs2.dueDate) from BillingStatement bs2 where bs2.loanID = :loanID and " +
			"dueDate <= :checkDate)")
	public BillingStatement findMostRecentBillForLoanWithDueDateOnOrBefore(@Param("loanID") Long loanID, @Param("checkDate") Date checkDate);
}
