package org.gsoft.openserv.rules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.gsoft.openserv.domain.loan.Disbursement;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanBalanceAdjustment;
import org.gsoft.openserv.repositories.loan.LoanBalanceAdjustmentRepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ResourceUtils;

public class AddDisbursementLoanBalanceAdjustmentTest {
	private HashMap<String, LoanBalanceAdjustment> returnValue = new HashMap<String, LoanBalanceAdjustment>();
	private StatelessKnowledgeSession session;
	
	@Before
	public void setup() throws FileNotFoundException {
		KnowledgeBuilder builder= KnowledgeBuilderFactory.newKnowledgeBuilder();
		builder.add(ResourceFactory.newFileResource(ResourceUtils.getFile("classpath:rules/AddDisbursementLoanBalanceAdjustment.drl")), ResourceType.DRL);
		if(builder.hasErrors())
			System.out.println(builder.getErrors());
		assertFalse("Errors loading DRL.", builder.hasErrors());
		KnowledgeBase base = KnowledgeBaseFactory.newKnowledgeBase();
		base.addKnowledgePackages(builder.getKnowledgePackages());
		session = base.newStatelessKnowledgeSession();

		LoanBalanceAdjustmentRepository lbaRepo = mock(LoanBalanceAdjustmentRepository.class);
		doAnswer(new Answer<LoanBalanceAdjustment>(){
			public LoanBalanceAdjustment answer(InvocationOnMock invocation){
				System.out.println("LoanBalanceAdjustmentRepository.save called.");
				assertTrue("Expected LoanBalanceAdjustment as argument.", invocation.getArguments().length == 1);
				assertTrue("Expected LoanBalanceAdjustment as argument.", invocation.getArguments()[0] != null);
				assertTrue("Expected LoanBalanceAdjustment as argument.", invocation.getArguments()[0] instanceof LoanBalanceAdjustment);
				returnValue.put("value", (LoanBalanceAdjustment)invocation.getArguments()[0]);
				ReflectionTestUtils.invokeSetterMethod(returnValue.get("value"), "setLoanBalanceAdjustmentID", 12345L);
				return (LoanBalanceAdjustment)invocation.getArguments()[0];
			}
		}).when(lbaRepo).save(any(LoanBalanceAdjustment.class));
		
		ApplicationContext springContext = mock(ApplicationContext.class);
		when(springContext.getBean(LoanBalanceAdjustmentRepository.class)).thenReturn(lbaRepo);
		
		session.setGlobal("springContext", springContext);
	}
	
	@Test
	public void testDisbDateAfterServicingStart(){
		final Loan loan = new Loan();
		loan.setLoanID(123L);
		loan.setServicingStartDate(new Date());
		final Disbursement disb = new Disbursement();
		disb.setLoan(loan);
		disb.setDisbursementAmount(100000);
		disb.setDisbursementEffectiveDate(new Date());
		

		HashMap<String,Date> sysDate = new HashMap<String, Date>();
		sysDate.put("SystemDate", new Date());
		session.execute(Arrays.asList(disb,sysDate));
		assertNotNull("Expected a LoanBalanceAdjustment to be created and saved.", returnValue.get("value"));
	 	assertTrue("Expected LoanBalanceAdjustment Principal Change to be " + disb.getDisbursementAmount() + ", but it was " + returnValue.get("value").getPrincipalChange(), disb.getDisbursementAmount().equals(returnValue.get("value").getPrincipalChange()));
	}

	@Test
	public void testDisbDateBeforeServicingStart() throws FileNotFoundException {
		final Loan loan = new Loan();
		final Disbursement disb = new Disbursement();
		disb.setLoan(loan);
		disb.setDisbursementAmount(100000);
		disb.setDisbursementEffectiveDate(new DateTime().minusDays(1).toDate());
		loan.setLoanID(123L);
		loan.setServicingStartDate(new Date());

		HashMap<String,Date> sysDate = new HashMap<String, Date>();
		sysDate.put("SystemDate", new Date());
		session.execute(Arrays.asList(disb,sysDate));
		assertNull("Expected a LoanBalanceAdjustment not to be created and saved.", returnValue.get("value"));
	}
}
