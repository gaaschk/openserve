package org.gsoft.openserv.repositories.loan;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanBalanceAdjustment;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class LoanBalanceAdjustmentRepository extends BaseRepository<LoanBalanceAdjustment, Long> {
	@Resource
	private LoanBalanceAdjustmentRepoIF loanBalanceRepo;

	@Override
	protected LoanBalanceAdjustmentRepoIF getSpringRepository() {
		return loanBalanceRepo;
	}
	
	public List<LoanBalanceAdjustment> findAllLoanBalanceAdjustmentsForLoan(Long loanID){
		return this.getSpringRepository().findAllByLoanID(loanID);
	}
	
	public Integer findNetPrincipalChangeForPeriod(Long loanID, Date fromDate, Date toDate){
		return this.getSpringRepository().findNetPrincipalChangeForPeriod(loanID, fromDate, toDate);
	}
	
	public Integer findNetPrincipalChangeThruDate(Long loanID, Date toDate){
		return this.getSpringRepository().findNetPrincipalChangeThruDate(loanID, toDate);
	}
}

@Repository
interface LoanBalanceAdjustmentRepoIF extends BaseSpringRepository<LoanBalanceAdjustment, Long>{
	
	List<LoanBalanceAdjustment> findAllByLoanID(Long loanID);
	
	@Query("SELECT SUM(lba.principalChange) FROM LoanBalanceAdjustment lba " +
			"WHERE lba.loanID = :loanID AND lba.effectiveDate >= :fromDate AND lba.effectiveDate <= :toDate")
	Integer findNetPrincipalChangeForPeriod(@Param("loanID") Long loanID, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	@Query("SELECT SUM(lba.principalChange) FROM LoanBalanceAdjustment lba " +
			"WHERE lba.loanID = :loanID AND lba.effectiveDate <= :toDate")
	Integer findNetPrincipalChangeThruDate(@Param("loanID") Long loanID, @Param("toDate") Date toDate);
	
	@Query("SELECT new Map(lba.effectiveDate as effectiveDate, lba.principalChange as principal) FROM LoanBalanceAdjustment lba " +
			"WHERE lba.loanID = :loanID AND lba.effectiveDate <= :toDate")
	List<Map<String,Integer>> findAllPrincipalChangesThruDate(@Param("loanID") Long loanID, @Param("toDate") Date toDate);

	@Query("SELECT new Map(lba.effectiveDate as effectiveDate, lba.principalChange as principal) FROM LoanBalanceAdjustment lba " +
			"WHERE lba.loanID = :loanID AND lba.effectiveDate >= :fromDate AND lba.effectiveDate <= :toDate")
	List<Map<String,Integer>> findAllPrincipalChangesFromDateToDate(@Param("loanID") Long loanID, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
} 
