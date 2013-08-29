package org.gsoft.openserv.repositories.repayment;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.repayment.RepaymentPlanSettings;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RepaymentPlanSettingsRepository extends BaseRepository<RepaymentPlanSettings, Long>{
	@Resource
	private RepaymentPlanSettingsSpringRepository springRepository;
	
	@Override
	protected RepaymentPlanSettingsSpringRepository getSpringRepository() {
		return this.springRepository;
	}
}

@Repository
interface RepaymentPlanSettingsSpringRepository extends BaseSpringRepository<RepaymentPlanSettings, Long>{

}
