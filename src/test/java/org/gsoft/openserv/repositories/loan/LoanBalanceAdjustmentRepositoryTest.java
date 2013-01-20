package org.gsoft.openserv.repositories.loan;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanBalanceAdjustment;
import org.gsoft.openserv.domain.loan.LoanType;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-context.xml")
@Transactional(readOnly=true)
public class LoanBalanceAdjustmentRepositoryTest {
	@Resource
	private LoanBalanceAdjustmentRepository loanBalAdjRepo;
	@Resource
	private LoanRepository loanRepository;
	
	private List<HashMap<String, PersistentDomainObject>> seedData;
	
	@Before
	@Rollback
	public void setup(){
		LoanBalanceAdjustment lba = new LoanBalanceAdjustment();
		lba.setEffectiveDate(new Date());
		lba.setFeesChange(0);
		lba.setInterestChange(100);
		lba.setPostDate(new Date());
		lba.setPrincipalChange(10000);
		HashMap<String, PersistentDomainObject> domainMap = new HashMap<String, PersistentDomainObject>();
		domainMap.put("lba", lba);
		
		Loan loan = new Loan();
		loan.setLoanType(LoanType.PRIVATE_STUDENT);
		loan.setStartingFees(0);
		loan.setStartingInterest(BigDecimal.ZERO);
		loan.setStartingPrincipal(0);
		domainMap.put("loan", loan);
		
		seedData = new ArrayList<HashMap<String,PersistentDomainObject>>();
		seedData.add(domainMap);
	}
	
	@Test
	@Rollback
	public void test() {
		Long loanID = 0L;
		for(HashMap<String, PersistentDomainObject> seed:seedData){
			Loan loan = loanRepository.save((Loan)seed.get("loan"));
			LoanBalanceAdjustment lba = (LoanBalanceAdjustment)seed.get("lba"); 
			lba.setLoanID(loan.getLoanID());
			loanID = loan.getLoanID();
			loanBalAdjRepo.save(lba);
		}
		List<LoanBalanceAdjustment> adjs = loanBalAdjRepo.findAllLoanBalanceAdjustmentsForLoan(loanID);
		assertNotNull("Expected at least one LoanBalanceAdjustment", adjs);
		assertTrue("Expected at least one LoanBalanceAdjustment", adjs.size() > 0);
		assertTrue("Expecting 1 LoanBalanceAdjustment, found " + adjs.size(), adjs.size() == 1);
		List<LoanBalanceAdjustment> changes = loanBalAdjRepo.findAllPrincipalChangesFromDateToDate(loanID, new DateTime().minusDays(1).toDate(), new DateTime().plusDays(1).toDate());
		assertNotNull("Expected at least one LoanBalanceAdjustment", changes);
		assertTrue("Expected at least one LoanBalanceAdjustment", changes.size() > 0);
		assertTrue("Expecting 1 LoanBalanceAdjustment, found " + changes.size(), changes.size() == 1);
	}

}
