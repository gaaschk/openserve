package org.gsoft.openserv.repositories.loan;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.OpenServDomainObject;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanAdjustmentType;
import org.gsoft.openserv.domain.loan.LoanBalanceAdjustment;
import org.gsoft.openserv.domain.loan.LoanType;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-context.xml")
public class LoanBalanceAdjustmentRepositoryTest {
	@Resource
	private LoanBalanceAdjustmentRepository loanBalAdjRepo;
	@Resource
	private LoanRepository loanRepository;
	
	private List<HashMap<String, OpenServDomainObject>> seedData;
	
	@Before
	public void setup(){
		LoanBalanceAdjustment lba = new LoanBalanceAdjustment();
		lba.setEffectiveDate(new Date());
		lba.setFeesChange(0);
		lba.setInterestChange(100);
		lba.setLoanAdjustmentType(LoanAdjustmentType.DISBURSEMENT);
		lba.setLoanID(123L);
		lba.setPostDate(new Date());
		lba.setPrincipalChange(10000);
		HashMap<String, OpenServDomainObject> domainMap = new HashMap<String, OpenServDomainObject>();
		domainMap.put("lba", lba);
		
		Loan loan = new Loan();
		loan.setLoanType(LoanType.PRIVATE_STUDENT);
		loan.setLoanID(123L);
		loan.setStartingFees(0);
		loan.setStartingInterest(BigDecimal.ZERO);
		loan.setStartingPrincipal(0);
		domainMap.put("loan", loan);
		
		seedData = Arrays.asList(domainMap);
	}
	
	@Test
	@Rollback
	public void test() {
		for(HashMap<String, OpenServDomainObject> seed:seedData){
			Loan loan = loanRepository.save((Loan)seed.get("loan"));
			LoanBalanceAdjustment lba = (LoanBalanceAdjustment)seed.get("lba"); 
			lba.setLoanID(loan.getLoanID());
			loanBalAdjRepo.save(lba);
		}
		loanBalAdjRepo.findAllLoanBalanceAdjustmentsForLoan(123L);
		Long totalPrincipal = loanBalAdjRepo.findNetPrincipalChangeForPeriod(123L, new DateTime().minusDays(1).toDate(), new DateTime().plusDays(1).toDate());
		List<Map<String, Integer>> changes = loanBalAdjRepo.findAllPrincipalChangesFromDateToDate(123L, new DateTime().minusDays(1).toDate(), new DateTime().plusDays(1).toDate());
		System.out.println("pause");
	}

}
