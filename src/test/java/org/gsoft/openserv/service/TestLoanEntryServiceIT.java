package org.gsoft.openserv.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.gsoft.openserv.Authenticator;
import org.gsoft.openserv.data.DatabaseUtility;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.service.loanentry.LoanEntryService;
import org.gsoft.openserv.util.LoanFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/application-context.xml")
public class TestLoanEntryServiceIT{
	@Resource
	private LoanEntryService loanEntryService;
	@Resource
	private DatabaseUtility dbutility;
	@Resource
	private LoanFactory loanFactory;
	@Resource
	private Authenticator authenticator;
	
	@Before
	public void refreshDatabase() throws IOException{
		dbutility.refreshDatabase();
	}
	
	@Test
	public void testAddLoan() throws IOException{
		authenticator.authenticate();
		Loan newLoan = loanFactory.getLoanByID(1);
		loanEntryService.addNewLoan(newLoan);
	}
}
