package org.gsoft.openserv.repositories.loan;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.config.CoreConfig;
import org.gsoft.openserv.config.PersistenceConfig;
import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(readOnly=true)
@ContextConfiguration(classes = {PersistenceConfig.class, CoreConfig.class}, loader = SpringApplicationContextLoader.class)
public class LoanRepositoryTest {
	@Resource
	private LoanRepository loanRepository;
	
	@Test
	@Rollback
	public void test() {
		Person borrower = new Person();
		borrower.setFirstName("John");
		borrower.setLastName("Doe");
		borrower.setSsn("100000000");
		Date today = new Date();
		Loan loan = new Loan();
		loan.setBorrower(borrower);
		loan.setFirstDueDate(today);
		loan.setInitialDueDate(today);
		LoanProgram loanType = new LoanProgram();
		loanType.setLoanProgramID(10L);
		loan.setLoanProgram(loanType);
		loan.setServicingStartDate(today);
		loan.setStartingFees(0);
		loan.setStartingInterest(BigDecimal.ZERO);
		loan.setStartingPrincipal(0);
		loan.setInitialUsedLoanTerm(180);
		loan = loanRepository.save(loan);
		assertNotNull("Expected primary key to be generated", loan.getLoanID());
		loan = loanRepository.findOne(loan.getLoanID());
		assertTrue("Expected loan term of 180 but was " + loan.getInitialUsedLoanTerm(), loan.getInitialUsedLoanTerm() == 180);
		loanRepository.findAll();
	}

}
