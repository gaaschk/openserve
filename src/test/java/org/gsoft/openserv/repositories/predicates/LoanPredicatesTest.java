package org.gsoft.openserv.repositories.predicates;

import java.util.Date;

import org.gsoft.openserv.domain.loan.LoanProgram;
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
		LoanProgram lt = new LoanProgram();
		lt.setLoanProgramID(10L);
		System.out.println(LoanPredicates.loanProgramIs(lt).toString());
	}

	@Test
	public void testAccountIDIs() {
		System.out.println(LoanPredicates.accountIDIs(1L).toString());
	}

}
