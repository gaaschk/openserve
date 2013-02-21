package org.gsoft.openserv.repositories.predicates;

import static org.junit.Assert.*;

import java.util.Date;

import org.gsoft.openserv.domain.loan.LoanType;
import org.junit.Test;

public class LoanPredicatesTest {

	@Test
	public void testBorrowerIdIs() {
		System.out.println(LoanPredicates.borrowerIdIs(1L).toString());
	}

	@Test
	public void testBorrowerIdIsAndActiveOnOrBefore() {
		System.out.println(LoanPredicates.borrowerIdIsAndActiveOnOrBefore(1L, new Date()).toString());
	}

	@Test
	public void testLoanTypeIs() {
		LoanType lt = new LoanType();
		lt.setLoanTypeID(10L);
		System.out.println(LoanPredicates.loanTypeIs(lt).toString());
	}

	@Test
	public void testAccountIDIs() {
		System.out.println(LoanPredicates.accountIDIs(1L).toString());
	}

}
