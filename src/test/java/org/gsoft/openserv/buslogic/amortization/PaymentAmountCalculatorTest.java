package org.gsoft.openserv.buslogic.amortization;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class PaymentAmountCalculatorTest {

	@Test
	public void testCalculatePaymentAmount() {
		int payment = PaymentAmountCalculator.calculatePaymentAmount(1000000, BigDecimal.valueOf(.0356), 180);
		assertEquals("Expected payment amount of 71.78", 7178, payment);
	}

}
