package org.gsoft.openserv.repositories.amortization;

import org.gsoft.openserv.domain.amortization.AmortizationSchedule;
import org.gsoft.openserv.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AmortizationScheduleRepository extends BaseSpringRepository<AmortizationSchedule, Long> {
	
	@Query("select lam from LoanAmortizationSchedule lam where lam.loanID = :loanID")
	public LoanAmortizationSchedule findScheduleForLoan(@Param("loanID") Long loanID);
}
