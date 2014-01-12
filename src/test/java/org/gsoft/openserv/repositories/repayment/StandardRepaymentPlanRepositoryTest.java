package org.gsoft.openserv.repositories.repayment;

import javax.annotation.Resource;

import org.gsoft.openserv.config.core.CoreConfig;
import org.gsoft.openserv.domain.loan.LoanPhaseEvent;
import org.gsoft.openserv.domain.repayment.StandardRepaymentPlan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(readOnly=true)
@ContextConfiguration(classes = {CoreConfig.class}, loader = SpringApplicationContextLoader.class)
public class StandardRepaymentPlanRepositoryTest {
	@Resource
	private StandardRepaymentPlanRepository standardPlanRepo;
		
	@Test
	@Rollback
	public void test() {
		StandardRepaymentPlan plan = new StandardRepaymentPlan();
		plan.setMaxLoanTerm(180);
		plan.setMinPaymentAmount(10);
		plan.setPlanStartDate(LoanPhaseEvent.FIRST_DISB_DATE);
		standardPlanRepo.save(plan);
		standardPlanRepo.findAll();
	}

}
