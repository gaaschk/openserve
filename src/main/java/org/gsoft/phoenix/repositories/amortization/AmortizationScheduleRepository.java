package org.gsoft.phoenix.repositories.amortization;

import org.gsoft.phoenix.domain.amortization.AmortizationSchedule;
import org.gsoft.phoenix.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.phoenix.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AmortizationScheduleRepository extends BaseRepository<AmortizationSchedule, Long> {
	
	@Query("select lam from LoanAmortizationSchedule lam where lam.loanID = :loanID")
	public LoanAmortizationSchedule findScheduleForLoan(@Param("loanID") Long loanID);
}
