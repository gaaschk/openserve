package org.gsoft.openserv.repositories.repayment;

import java.util.List;

import org.gsoft.openserv.domain.repayment.FixedRepaymentPlan;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedRepaymentPlanRepository extends BaseRepository<FixedRepaymentPlan, Long>{

	@Query("select rp from FixedRepaymentPlan rp where rp.defaultLoanProgramSettings.defaultLoanProgramSettingsID = :loanSettingsID")
	List<FixedRepaymentPlan> findAllRepaymentPlansForDefaultLoanSettings(@Param("loanSettingsID") Long loanSettingsID);
}