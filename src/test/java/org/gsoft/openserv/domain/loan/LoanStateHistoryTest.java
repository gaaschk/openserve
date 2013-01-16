package org.gsoft.openserv.domain.loan;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.domain.rates.RateValue;
import org.joda.time.DateTime;
import org.junit.Test;

public class LoanStateHistoryTest {

	@Test
	public void test() {
		Date today = new Date();
		LoanStateHistory history = new LoanStateHistory();
		Disbursement disbursement = new Disbursement();
		disbursement.setDisbursementAmount(10000000);
		disbursement.setDisbursementEffectiveDate(today);
		history.addDisbursement(disbursement);
		LoanRateValue loanRate = new LoanRateValue();
		loanRate.setMarginValue(BigDecimal.ZERO);
		loanRate.setLockedDate(today);
		RateValue rateValue = new RateValue();
		rateValue.setRateValue(BigDecimal.valueOf(.035));
		loanRate.setRateValue(rateValue);
		history.addRateChange(loanRate);
		LoanBalanceAdjustment adj = new LoanBalanceAdjustment();
		adj.setEffectiveDate(new DateTime(today).plusDays(15).toDate());
		adj.setPostDate(today);
		adj.setPrincipalChange(10000);
		adj.setInterestChange(10000);
		adj.setFeesChange(5000);
		history.addAdjustment(adj);
		Date paymentDate = new DateTime(today).plusDays(100).toDate();
		Payment payment = new Payment();
		payment.setEffectiveDate(paymentDate);
		payment.setPostDate(paymentDate);
		LoanPayment loanPayment = new LoanPayment();
		loanPayment.setPayment(payment);
		loanPayment.setAppliedAmount(-50000);
		history.addPayment(loanPayment);
		paymentDate = new DateTime(today).plusDays(90).toDate();
		payment = new Payment();
		payment.setEffectiveDate(paymentDate);
		payment.setPostDate(paymentDate);
		loanPayment = new LoanPayment();
		loanPayment.setPayment(payment);
		loanPayment.setAppliedAmount(-500000);
		history.addPayment(loanPayment);
		loanRate = new LoanRateValue();
		loanRate.setLockedDate(new DateTime(today).plusDays(30).toDate());
		loanRate.setMarginValue(BigDecimal.ZERO);
		rateValue = new RateValue();
		rateValue.setRateValue(BigDecimal.valueOf(.043));
		loanRate.setRateValue(rateValue);
		history.addRateChange(loanRate);
		System.out.println(history);
		assertTrue("Expecting principal to be " + 9585799, history.getEndingPrincipal() == 9585799);
	}
}
