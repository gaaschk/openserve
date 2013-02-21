package org.gsoft.openserv.repositories.amortization;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.amortization.AmortizationSchedule;
import org.gsoft.openserv.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class AmortizationScheduleRepository extends BaseRepository<AmortizationSchedule, Long> {
	@Resource
	private AmortizationScheduleSpringRepository amortizationScheduleSpringRepository;

	@Override
	protected AmortizationScheduleSpringRepository getSpringRepository() {
		return amortizationScheduleSpringRepository;
	}
	
	public LoanAmortizationSchedule findScheduleForLoan(Long loanID){
		return this.amortizationScheduleSpringRepository.findScheduleForLoan(loanID);
	}

	public List<LoanAmortizationSchedule> findAllSchedulesForLoan(Long loanID){
		return this.getSpringRepository().findAllSchedulesForLoan(loanID);
	}

	public LoanAmortizationSchedule findScheduleForLoanEffectiveOn(Long loanID, Date effectiveDate){
		return this.getSpringRepository().findScheduleForLoanEffectiveOn(loanID, effectiveDate);
	}

	public LoanAmortizationSchedule findScheduleForLoanWithEffectiveDate(Long loanID, Date effectiveDate){
		return this.getSpringRepository().findScheduleForLoanWithEffectiveDate(loanID, effectiveDate);
	}
	
	public List<AmortizationSchedule> findAllSchedulesForAccountThroughDate(Long accountID, Date effectiveDate){
		return this.getSpringRepository().findAllSchedulesForAccountThroughDate(accountID, effectiveDate);
	}
}

@Repository
interface AmortizationScheduleSpringRepository extends BaseSpringRepository<AmortizationSchedule, Long>{
	@Query("select lam from LoanAmortizationSchedule lam where lam.loanID = :loanID and lam.amortizationSchedule.effectiveDate = " +
			"(SELECT MAX(lam2.amortizationSchedule.effectiveDate) FROM LoanAmortizationSchedule lam2 where lam2.loanID = :loanID)")
	LoanAmortizationSchedule findScheduleForLoan(@Param("loanID") Long loanID);
	
	@Query("select lam from LoanAmortizationSchedule lam where lam.loanID = :loanID and lam.amortizationSchedule.effectiveDate = " +
			"(SELECT MAX(lam2.amortizationSchedule.effectiveDate) FROM LoanAmortizationSchedule lam2 where lam2.loanID = :loanID and lam2.amortizationSchedule.effectiveDate <= :effectiveDate)")
	LoanAmortizationSchedule findScheduleForLoanEffectiveOn(@Param("loanID") Long loanID, @Param("effectiveDate") Date effectiveDate);

	@Query("select lam from LoanAmortizationSchedule lam where lam.loanID = :loanID and lam.amortizationSchedule.effectiveDate = :effectiveDate")
	LoanAmortizationSchedule findScheduleForLoanWithEffectiveDate(@Param("loanID") Long loanID, @Param("effectiveDate") Date effectiveDate);

	@Query("SELECT lam FROM LoanAmortizationSchedule lam WHERE lam.loanID = :loanID")
	List<LoanAmortizationSchedule> findAllSchedulesForLoan(@Param("loanID") Long loanID);

	@Query("select ams from AmortizationSchedule ams where ams.accountID = :accountID and ams.effectiveDate <= :effectiveDate and ams.invalid <> true")
	List<AmortizationSchedule> findAllSchedulesForAccountThroughDate(@Param("accountID") Long accountID, @Param("effectiveDate") Date effectiveDate);
} 
