package org.gsoft.openserv.repositories.amortization;

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
	protected BaseSpringRepository<AmortizationSchedule, Long> getSpringRepository() {
		return amortizationScheduleSpringRepository;
	}
	
	public LoanAmortizationSchedule findScheduleForLoan(@Param("loanID") Long loanID){
		return this.amortizationScheduleSpringRepository.findScheduleForLoan(loanID);
	}
}

@Repository
interface AmortizationScheduleSpringRepository extends BaseSpringRepository<AmortizationSchedule, Long>{
	@Query("select lam from LoanAmortizationSchedule lam where lam.loanID = :loanID")
	public LoanAmortizationSchedule findScheduleForLoan(@Param("loanID") Long loanID);
} 
