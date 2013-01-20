package org.gsoft.openserv.repositories.payment;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.payment.BillingStatement;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class BillingStatementRepository extends BaseRepository<BillingStatement, Long>{
	@Resource
	private BillingStatementSpringRepository billingStatementSpringRepository;

	@Override
	protected BillingStatementSpringRepository getSpringRepository() {
		return this.billingStatementSpringRepository;
	}
	
	public BillingStatement findMostRecentBillingStatementForLoan(Long loanID){
		return this.billingStatementSpringRepository.findMostRecentBillingStatementForLoan(loanID);
	}

	public List<BillingStatement> findAllBillsForLoanWithPaymentsMadeOnOrAfterOrUnpaidByOrDueAfter(Long loanID, Date effectiveDate, Date satisfiedDate, Date dueDate){
		return this.billingStatementSpringRepository.findAllBillsForLoanWithPaymentsMadeOnOrAfterOrUnpaidByOrDueAfter(loanID, effectiveDate, satisfiedDate, dueDate);
	}

	public List<BillingStatement> findAllBillsForLoan(Long loanID){
		return this.billingStatementSpringRepository.findAllBillsForLoan(loanID);
	}
	
	public List<BillingStatement> findAllBillsForLoanOnOrBefore(Long loanID, Date asOfDate){
		return this.billingStatementSpringRepository.findAllBillsForLoanOnOrBefore(loanID, asOfDate);
	}

	public BillingStatement findEarliestUnpaidForLoan(Long loanID){
		return this.getSpringRepository().findEarliestUnpaid(loanID);
	}
}

@Repository
interface BillingStatementSpringRepository extends BaseSpringRepository<BillingStatement, Long>{
	
	@Query("select bs from BillingStatement bs where bs.loanID = :loanID and bs.dueDate = " +
			"(select max(bs2.dueDate) from BillingStatement bs2 where bs2.loanID = :loanID)")
	BillingStatement findMostRecentBillingStatementForLoan(@Param("loanID") Long loanID);

	@Query("select distinct bs from BillingStatement bs " +
			"where bs.loanID = :loanID and " + 
			"(bs.satisfiedDate is null or " +
			"bs.satisfiedDate >= :satisfiedDate or " +
			"bs.dueDate >= :dueDate or " +
			"bs.billingStatementID in (" +
			"select bp.billingStatement.billingStatementID from BillPayment bp where " +
			"bp.loanPayment.payment.effectiveDate >= :effectiveDate " +
			")) order by bs.dueDate desc")
	List<BillingStatement> findAllBillsForLoanWithPaymentsMadeOnOrAfterOrUnpaidByOrDueAfter(@Param("loanID") Long loanID, @Param("effectiveDate") Date effectiveDate, @Param("satisfiedDate") Date satisfiedDate, @Param("dueDate") Date dueDate);

	@Query("select bs from BillingStatement bs where bs.loanID = :loanID order by bs.dueDate desc")
	List<BillingStatement> findAllBillsForLoan(@Param("loanID") Long loanID);
	
	//TODO make this by statementDate
	@Query("select bs from BillingStatement bs where bs.loanID = :loanID and bs.dueDate <= :asOfDate order by bs.dueDate desc")
	List<BillingStatement> findAllBillsForLoanOnOrBefore(@Param("loanID") Long loanID, @Param("asOfDate") Date asOfDate);

	@Query("select bs from BillingStatement bs where bs.loanID = :loanID and bs.satisfiedDate is null and bs.dueDate = " +
			"(select min(bs2.dueDate) from BillingStatement bs2 where bs2.loanID = :loanID and bs2.satisfiedDate is null)")
	BillingStatement findEarliestUnpaid(@Param("loanID") Long loanID);
}
