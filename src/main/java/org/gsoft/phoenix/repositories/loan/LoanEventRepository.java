package org.gsoft.phoenix.repositories.loan;

import java.util.Date;

import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanEventRepository extends BaseRepository<LoanEvent, Long>{
	
	@Query("select le from LoanEvent le where le.loanTransaction is not null and " +
			"le.loanEventID = (select max(le2.loanEventID) from LoanEvent le2 where le2.loanID = :loanID and le2.effectiveDate <= :checkDate)")
	public LoanEvent findMostRecentLoanEventWithTransactionEffectivePriorToDate(@Param("loanID") Long loanID, @Param("checkDate") Date checkDate);

	@Query("select le from LoanEvent le where le.loanTransaction is not null and " +
			"le.loanEventID = (select max(le2.loanEventID) from LoanEvent le2 where le2.loanID = :loanID)")
	public LoanEvent findMostRecentLoanEventWithTransaction(@Param("loanID") Long loanID);
}
