package org.gsoft.openserv.repositories.payment;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.payment.BillPayment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-context.xml")
public class BillPaymentRepositoryTest {
	@Resource
	private BillPaymentRepository billPaymentRepo;
	
	@Test
	public void test() {
		BillPayment payment = new BillPayment();
		payment.setAmountAppliedToBill(10000);
		payment = billPaymentRepo.save(payment);
		assertNotNull("Expected primary key to be generated.", payment.getBillPaymentID());
		payment = billPaymentRepo.findOne(payment.getBillPaymentID());
		assertNotNull("Expected to find payment.", payment);
	}

}
