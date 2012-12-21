package org.gsoft.openserv.buslogic.amortization;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.gsoft.openserv.buslogic.interest.InterestAccrualLogic;
import org.gsoft.openserv.domain.amortization.AmortizationSchedule;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.amortization.AmortizationScheduleRepository;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class AmortizationLogicTest {

	@Test
	public void testCreateAmortizationSchedule() {
		Loan testLoan = mock(Loan.class);
		when(testLoan.getCurrentPrincipal()).thenReturn(1000000);
		when(testLoan.getRemainingLoanTerm()).thenReturn(100);
		when(testLoan.getLoanID()).thenReturn(1L);
		
		PaymentAmountCalculator pmtCalc = mock(PaymentAmountCalculator.class);
		Integer paymentAmount = 10000;
		when(pmtCalc.calculatePaymentAmount(anyInt(), any(BigDecimal.class), anyInt())).thenReturn(paymentAmount);
		
		InterestAccrualLogic interestLogic = mock(InterestAccrualLogic.class);
		when(interestLogic.getInterestRateForLoan(any(Loan.class))).thenReturn(BigDecimal.ZERO);
		
		AmortizationScheduleRepository amortRepo = mock(AmortizationScheduleRepository.class);
		
		LoanRepository loanRepo = mock(LoanRepository.class);
		when(loanRepo.findOne(any(Long.class))).thenReturn(testLoan);
		
		AmortizationLogic amortLogic = new AmortizationLogic();
		ReflectionTestUtils.setField(amortLogic, "paymentCalculator", pmtCalc);
		ReflectionTestUtils.setField(amortLogic, "interestLogic", interestLogic);
		ReflectionTestUtils.setField(amortLogic, "amortizationScheduleRepository", amortRepo);
		ReflectionTestUtils.setField(amortLogic, "loanRepository", loanRepo);

		AmortizationSchedule amortSched = amortLogic.createAmortizationSchedule(Arrays.asList(1L), new Date());
		assertNotNull("Schedule not generated.",amortSched);
		assertNotNull("Loan Schedule not generated.", amortSched.getLoanAmortizationSchedules());
		assertNotNull("No payments generated for loan schedule.", amortSched.getLoanAmortizationSchedules().get(0).getAmortizationLoanPayments());
		assertTrue("Payment Amount should equal " + paymentAmount + ".", amortSched.getLoanAmortizationSchedules().get(0).getAmortizationLoanPayments().get(0).getPaymentAmount().equals(paymentAmount));
	}

}
