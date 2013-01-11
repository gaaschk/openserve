package org.gsoft.openserv.repositories.payment;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.payment.BillingStatement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-context.xml")
public class BillingStatementRepositoryTest {
	@Resource
	private BillingStatementRepository repository;
	
	@Test
	public void test() {
		Date today = new Date();
		BillingStatement statement = new BillingStatement();
		statement.setCreatedDate(today);
		statement.setDueDate(today);
		statement.setMinimumRequiredPayment(1000);
		statement.setPaidAmount(0);
		statement = repository.save(statement);
		assertNotNull("Expected primary key to be set", statement.getBillingStatementID());
		statement = repository.findOne(statement.getBillingStatementID());
	}

}
