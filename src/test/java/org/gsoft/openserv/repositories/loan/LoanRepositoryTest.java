package org.gsoft.openserv.repositories.loan;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-context.xml")
public class LoanRepositoryTest {
	@Resource
	private LoanRepository loanRepository;
	
	@Test
	public void test() {
		Person borrower = new Person();
		borrower.setFirstName("John");
		borrower.setLastName("Doe");
		borrower.setSsn("000000000");
		Date today = new Date();
		Loan loan = new Loan();
		loan.setBorrower(borrower);
		loan.setCurrentUnpaidDueDate(today);
		loan.setFirstDueDate(today);
		loan.setInitialDueDate(today);
		loan.setLoanType(LoanType.PRIVATE_STUDENT);
		loan.setMinimumPaymentAmount(10000);
		loan.setNextDueDate(today);
		loan.setRepaymentStartDate(today);
		loan.setServicingStartDate(today);
		loan.setStartingFees(0);
		loan.setStartingInterest(BigDecimal.ZERO);
		loan.setStartingPrincipal(0);
		loan.setStartingLoanTerm(180);
		loan = loanRepository.save(loan);
		assertNotNull("Expected primary key to be generated", loan.getLoanID());
		loan = loanRepository.findOne(loan.getLoanID());
		assertTrue("Expected loan term of 180 but was " + loan.getStartingLoanTerm(), loan.getStartingLoanTerm() == 180);
	}

}
