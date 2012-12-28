package org.gsoft.openserv.buslogic.interest;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanBalanceAdjustment;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.repositories.LoanRateValueRepository;
import org.gsoft.openserv.repositories.loan.LoanBalanceAdjustmentRepository;
import org.gsoft.openserv.util.Constants;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class InterestCalculatorTest {
	
	@Test
	public void testCalculateLoanInterestAmountForPeriod_NoRateOrPrinChanges() {
		Long loanID = 1L;
		Date fromDate = new DateTime().minusDays(100).toDate();
		Date toDate = new DateTime().plusDays(100).toDate();
		int startingPrincipal = 100000;
		
		RateValue startingRate = new RateValue();
		startingRate.setRateValue(BigDecimal.valueOf(.25));
		LoanRateValue startingLoanRate = new LoanRateValue();
		startingLoanRate.setLockedDate(fromDate);
		startingLoanRate.setRateValue(startingRate);

		ArrayList<LoanRateValue> rateChanges = new ArrayList<LoanRateValue>();
		rateChanges.add(startingLoanRate);
		LoanRateValueRepository lrvRepo = this.getLoanRateValueRepository(loanID, fromDate, toDate, startingLoanRate, rateChanges);
		
		InterestCalculator calculator = new InterestCalculator();
		ReflectionTestUtils.setField(calculator, "loanRateValueRepo", lrvRepo);
		

		LoanBalanceAdjustment lba = new LoanBalanceAdjustment();
		lba.setEffectiveDate(fromDate);
		lba.setPrincipalChange(startingPrincipal);
		
		ArrayList<LoanBalanceAdjustment> prinChanges = new ArrayList<LoanBalanceAdjustment>();
		prinChanges.add(lba);
		
		LoanBalanceAdjustmentRepository lbaRepo = this.getLoanBalanceAdjustmentRepository(loanID, fromDate, toDate, startingPrincipal, prinChanges);
		ReflectionTestUtils.setField(calculator, "loanBalanceAdjustmentRepo", lbaRepo);
		
		Loan loan = this.getLoan(loanID, fromDate);
		BigDecimal totalInterest = calculator.calculateLoanInterestAmountForPeriod(loan, fromDate, toDate);
		assertNotNull("Expected a non null value for total interest", totalInterest);
		BigDecimal dailyRate = startingRate.getRateValue().divide(BigDecimal.valueOf(Constants.DAYS_IN_YEAR), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		BigDecimal expectedInterest = dailyRate.multiply(BigDecimal.valueOf(startingPrincipal)).multiply(BigDecimal.valueOf(Days.daysBetween(new DateTime(fromDate), new DateTime(toDate)).getDays()));
		expectedInterest = expectedInterest.setScale(Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		assertTrue("Expected interest to be " + expectedInterest + " but was " + totalInterest, expectedInterest.equals(totalInterest));
	}
	
	@Test
	public void testCalculateLoanInterestAmountForPeriod_NoRateChanges_PrinChanges() {
		Long loanID = 1L;
		Date fromDate = new DateTime().minusDays(100).toDate();
		Date toDate = new DateTime().plusDays(100).toDate();
		int startingPrincipal = 100000;
		
		RateValue startingRate = new RateValue();
		startingRate.setRateValue(BigDecimal.valueOf(.25));
		LoanRateValue startingLoanRate = new LoanRateValue();
		startingLoanRate.setLockedDate(fromDate);
		startingLoanRate.setRateValue(startingRate);

		ArrayList<LoanRateValue> rateChanges = new ArrayList<LoanRateValue>();
		rateChanges.add(startingLoanRate);
		LoanRateValueRepository lrvRepo = this.getLoanRateValueRepository(loanID, fromDate, toDate, startingLoanRate, rateChanges);
		
		InterestCalculator calculator = new InterestCalculator();
		ReflectionTestUtils.setField(calculator, "loanRateValueRepo", lrvRepo);
		
		LoanBalanceAdjustment lba = new LoanBalanceAdjustment();
		lba.setEffectiveDate(fromDate);
		lba.setPrincipalChange(startingPrincipal);
		
		ArrayList<LoanBalanceAdjustment> prinChanges = new ArrayList<LoanBalanceAdjustment>();
		prinChanges.add(lba);

		lba = new LoanBalanceAdjustment();
		lba.setEffectiveDate(new DateTime(fromDate).plusDays(100).toDate());
		lba.setPrincipalChange(startingPrincipal);
		prinChanges.add(lba);

		LoanBalanceAdjustmentRepository lbaRepo = this.getLoanBalanceAdjustmentRepository(loanID, fromDate, toDate, startingPrincipal, prinChanges);
		ReflectionTestUtils.setField(calculator, "loanBalanceAdjustmentRepo", lbaRepo);
		
		Loan loan = this.getLoan(loanID, fromDate);
		BigDecimal totalInterest = calculator.calculateLoanInterestAmountForPeriod(loan, fromDate, toDate);
		assertNotNull("Expected a non null value for total interest", totalInterest);
		BigDecimal dailyRate = startingRate.getRateValue().divide(BigDecimal.valueOf(Constants.DAYS_IN_YEAR), Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		BigDecimal expectedInterest = dailyRate.multiply(BigDecimal.valueOf(startingPrincipal)).multiply(BigDecimal.valueOf(100));
		expectedInterest = expectedInterest.add(dailyRate.multiply(BigDecimal.valueOf(startingPrincipal*2)).multiply(BigDecimal.valueOf(100)));
		expectedInterest = expectedInterest.setScale(Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		assertTrue("Expected interest to be " + expectedInterest + " but was " + totalInterest, expectedInterest.equals(totalInterest));
	}

	private Loan getLoan(Long loanID, Date servicingStartDate){
		Loan loan = new Loan();
		loan.setLoanID(loanID);
		loan.setServicingStartDate(servicingStartDate);
		return loan;
	}
	
	private LoanRateValueRepository getLoanRateValueRepository(Long loanID, Date fromDate, Date toDate, 
			LoanRateValue startingLoanRate, List<LoanRateValue> allRateChanges){
		LoanRateValueRepository lrvRepo = mock(LoanRateValueRepository.class);
		when(lrvRepo.findLoanRateValueForLoanAsOf(loanID, fromDate)).thenReturn(startingLoanRate);
		when(lrvRepo.findAllLoanRateValuesFromDateToDate(loanID, fromDate, toDate)).thenReturn(allRateChanges);
		return lrvRepo;
	}
	
	private LoanBalanceAdjustmentRepository getLoanBalanceAdjustmentRepository(Long loanID, Date fromDate, Date toDate, 
			int startingPrincipal, ArrayList<LoanBalanceAdjustment> adjustments){
		LoanBalanceAdjustmentRepository lbaRepo = mock(LoanBalanceAdjustmentRepository.class);
		when(lbaRepo.findNetPrincipalChangeThruDate(loanID, fromDate)).thenReturn(startingPrincipal);
		when(lbaRepo.findAllPrincipalChangesFromDateToDate(loanID, fromDate, toDate)).thenReturn(adjustments);
		return lbaRepo;
	}
}
