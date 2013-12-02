package org.gsoft.openserv.repositories.loan;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.loan.LoanBalanceAdjustment;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanBalanceAdjustmentRepository extends BaseRepository<LoanBalanceAdjustment, Long>{
	
	List<LoanBalanceAdjustment> findAllByLoanID(Long loanID);
	
	@Query("SELECT lba FROM LoanBalanceAdjustment lba " +
			"WHERE lba.loanID = :loanID AND lba.effectiveDate <= :toDate ORDER BY lba.effectiveDate asc, lba.postDate asc")
	List<LoanBalanceAdjustment> findAllForLoanThruDate(@Param("loanID") Long loanID, @Param("toDate") Date toDate);
	
	@Query("SELECT lba FROM LoanBalanceAdjustment lba " +
			"WHERE lba.loanID = :loanID AND lba.effectiveDate <= :toDate AND lba.principalChange <> 0")
	List<LoanBalanceAdjustment> findAllPrincipalChangesThruDate(@Param("loanID") Long loanID, @Param("toDate") Date toDate);

	@Query("SELECT lba FROM LoanBalanceAdjustment lba " +
			"WHERE lba.loanID = :loanID AND lba.effectiveDate >= :fromDate AND lba.effectiveDate <= :toDate AND principalChange <> 0")
	List<LoanBalanceAdjustment> findAllPrincipalChangesFromDateToDate(@Param("loanID") Long loanID, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}