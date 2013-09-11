package org.gsoft.openserv.repositories.repayment;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.repayment.FixedRepaymentPlan;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class FixedRepaymentPlanRepository extends BaseRepository<FixedRepaymentPlan, Long>{
	@Resource
	private FixedRepaymentPlanSpringRepository springRepository;
	
	@Override
	protected FixedRepaymentPlanSpringRepository getSpringRepository() {
		return this.springRepository;
	}

	public List<FixedRepaymentPlan> findAllRepaymentPlansForDefaultLoanSettings(Long loanSettingsID){
		return this.getSpringRepository().findAllRepaymentPlansForDefaultLoanSettings(loanSettingsID);
	}
}

@Repository
interface FixedRepaymentPlanSpringRepository extends BaseSpringRepository<FixedRepaymentPlan, Long>{

	@Query("select rp from FixedRepaymentPlan rp where rp.defaultLoanProgramSettings.defaultLoanProgramSettingsID = :loanSettingsID")
	List<FixedRepaymentPlan> findAllRepaymentPlansForDefaultLoanSettings(@Param("loanSettingsID") Long loanSettingsID);
}
