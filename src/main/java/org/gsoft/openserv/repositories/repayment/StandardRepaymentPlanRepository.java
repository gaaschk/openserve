package org.gsoft.openserv.repositories.repayment;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.repayment.StandardRepaymentPlan;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class StandardRepaymentPlanRepository extends BaseRepository<StandardRepaymentPlan, Long>{
	@Resource
	private StandardRepaymentPlanSpringRepository springRepository;
	
	@Override
	protected StandardRepaymentPlanSpringRepository getSpringRepository() {
		return this.springRepository;
	}

	public List<StandardRepaymentPlan> findAllRepaymentPlansForDefaultLoanSettings(Long loanSettingsID){
		return this.getSpringRepository().findAllRepaymentPlansForDefaultLoanSettings(loanSettingsID);
	}
}

@Repository
interface StandardRepaymentPlanSpringRepository extends BaseSpringRepository<StandardRepaymentPlan, Long>{
	
	@Query("select rp from StandardRepaymentPlan rp where rp.defaultLoanProgramSettings.defaultLoanProgramSettingsID = :loanSettingsID")
	List<StandardRepaymentPlan> findAllRepaymentPlansForDefaultLoanSettings(@Param("loanSettingsID") Long loanSettingsID);
}
