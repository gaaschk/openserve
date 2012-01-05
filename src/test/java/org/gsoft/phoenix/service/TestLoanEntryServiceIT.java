package org.gsoft.phoenix.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.gsoft.phoenix.data.DatabaseUtility;
import org.gsoft.phoenix.service.domain.LoanEntryDocument;
import org.gsoft.phoenix.util.LoanFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/application-context.xml")
public class TestLoanEntryServiceIT {
	@Resource
	private LoanEntryService loanEntryService;
	@Resource
	private DatabaseUtility dbutility;
	@Resource
	private LoanFactory loanFactory;
	
	@Before
	public void refreshDatabase() throws IOException{
		dbutility.refreshDatabase();
	}
	
	@Test
	public void testAddLoan() throws IOException{
		LoanEntryDocument newLoan = loanFactory.getLoanByID(1);
		Long loanid = loanEntryService.addNewLoan(newLoan);
	}
}
