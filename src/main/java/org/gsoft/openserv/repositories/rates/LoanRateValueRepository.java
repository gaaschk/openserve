package org.gsoft.openserv.repositories.rates;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRateValueRepository extends BaseRepository<LoanRateValue, Long>{

	@Query("SELECT lrv FROM LoanRateValue lrv " +
			"WHERE lrv.loanID = :loanID order by lrv.lockedDate asc")
	List<LoanRateValue> findAllLoanRateValues(@Param("loanID") Long loanID);

	@Query("SELECT lrv FROM LoanRateValue lrv " +
			"WHERE lrv.loanID = :loanID AND lrv.lockedDate <= :toDate order by lrv.lockedDate asc")
	List<LoanRateValue> findAllLoanRateValuesThruDate(@Param("loanID") Long loanID, @Param("toDate") Date toDate);

	@Query("SELECT lrv FROM LoanRateValue lrv " +
			"WHERE lrv.loanID = :loanID AND lrv.lockedDate >= :fromDate AND lrv.lockedDate <= :toDate")
	List<LoanRateValue> findAllLoanRateValuesFromDateToDate(@Param("loanID") Long loanID, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
	@Query("SELECT lrv FROM LoanRateValue lrv WHERE lrv.loanID = :loanID AND lrv.lockedDate = " +
			"(SELECT MAX(lrv2.lockedDate) FROM LoanRateValue lrv2 WHERE lrv2.loanID = :loanID AND lrv2.lockedDate <= :asOfDate)")
	LoanRateValue findLoanRateValueForLoanAsOf(@Param("loanID") Long loanID, @Param("asOfDate") Date asOfDate);
	
	@Query("SELECT lrv FROM LoanRateValue lrv WHERE lrv.loanID = :loanID AND lrv.lockedDate = (" +
			"SELECT MAX(lrv2.lockedDate) FROM LoanRateValue lrv2 WHERE lrv2.loanID = :loanID)")
	LoanRateValue findMostRecentForLoan(@Param("loanID") Long loanID);
}