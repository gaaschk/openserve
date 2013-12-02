package org.gsoft.openserv.repositories.amortization;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.amortization.AmortizationSchedule;
import org.gsoft.openserv.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AmortizationScheduleRepository extends BaseRepository<AmortizationSchedule, Long>{
	@Query("select lam from LoanAmortizationSchedule lam where lam.loanID = :loanID and lam.amortizationSchedule.effectiveDate = " +
			"(SELECT MAX(lam2.amortizationSchedule.effectiveDate) FROM LoanAmortizationSchedule lam2 where lam2.loanID = :loanID)")
	LoanAmortizationSchedule findScheduleForLoan(@Param("loanID") Long loanID);
	
	@Query("select lam from LoanAmortizationSchedule lam where lam.loanID = :loanID and lam.amortizationSchedule.effectiveDate = " +
			"(SELECT MAX(lam2.amortizationSchedule.effectiveDate) FROM LoanAmortizationSchedule lam2 where lam2.loanID = :loanID and lam2.amortizationSchedule.effectiveDate <= :effectiveDate " +
			"and lam2.amortizationSchedule.invalid <> true)")
	LoanAmortizationSchedule findScheduleForLoanEffectiveOn(@Param("loanID") Long loanID, @Param("effectiveDate") Date effectiveDate);

	@Query("select lam from LoanAmortizationSchedule lam where lam.loanID = :loanID and lam.amortizationSchedule.effectiveDate = :effectiveDate")
	LoanAmortizationSchedule findScheduleForLoanWithEffectiveDate(@Param("loanID") Long loanID, @Param("effectiveDate") Date effectiveDate);

	@Query("SELECT lam FROM LoanAmortizationSchedule lam WHERE lam.loanID = :loanID")
	List<LoanAmortizationSchedule> findAllSchedulesForLoan(@Param("loanID") Long loanID);

	@Query("select ams from AmortizationSchedule ams where ams.accountID = :accountID and ams.effectiveDate <= :effectiveDate and ams.invalid <> true")
	List<AmortizationSchedule> findAllSchedulesForAccountThroughDate(@Param("accountID") Long accountID, @Param("effectiveDate") Date effectiveDate);
}