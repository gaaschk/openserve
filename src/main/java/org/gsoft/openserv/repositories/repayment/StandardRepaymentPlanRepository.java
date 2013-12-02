package org.gsoft.openserv.repositories.repayment;

import java.util.List;

import org.gsoft.openserv.domain.repayment.StandardRepaymentPlan;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardRepaymentPlanRepository extends BaseRepository<StandardRepaymentPlan, Long>{
	
	@Query("select rp from StandardRepaymentPlan rp where rp.defaultLoanProgramSettings.defaultLoanProgramSettingsID = :loanSettingsID")
	List<StandardRepaymentPlan> findAllRepaymentPlansForDefaultLoanSettings(@Param("loanSettingsID") Long loanSettingsID);
}