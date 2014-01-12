package org.gsoft.openserv.repositories.payment;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.config.core.CoreConfig;
import org.gsoft.openserv.domain.payment.billing.BillingStatement;
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
public class BillingStatementRepositoryTest {
	@Resource
	private BillingStatementRepository repository;
	
	@Test
	@Rollback
	public void test() {
		Date today = new Date();
		BillingStatement statement = new BillingStatement();
		statement.setCreatedDate(today);
		statement.setDueDate(today);
		statement.setMinimumRequiredPayment(1000);
		statement = repository.save(statement);
		assertNotNull("Expected primary key to be set", statement.getBillingStatementID());
		statement = repository.findOne(statement.getBillingStatementID());
	}

}
