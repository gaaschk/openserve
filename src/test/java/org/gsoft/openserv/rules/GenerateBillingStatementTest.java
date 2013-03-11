package org.gsoft.openserv.rules;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.gsoft.openserv.buslogic.payment.BillingStatementLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.domain.payment.billing.BillingStatement;
import org.gsoft.openserv.repositories.payment.BillingStatementRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ResourceUtils;

public class GenerateBillingStatementTest {
	
	@Test
	public void test() throws IOException {
		Loan loan = new Loan();
		loan.setLoanID(1L);
		
		DefaultLoanProgramSettings ltp = new DefaultLoanProgramSettings();
		ltp.setDefaultLoanProgramSettingsID(1L);
		ltp.setDaysBeforeDueToBill(20);
		
		BillingStatement lastStatement = new BillingStatement();
		lastStatement.setLoanID(1L);
		DateTime statementDueDate = new DateTime(new Date().getTime());
		statementDueDate = statementDueDate.minusDays(1);
		lastStatement.setDueDate(statementDueDate.toDate());

		BillingStatement newStatement = new BillingStatement();
		newStatement.setLoanID(1L);
		DateTime newStatementDueDate = new DateTime(new Date().getTime());
		newStatementDueDate = newStatementDueDate.plusDays(1);
		newStatement.setDueDate(newStatementDueDate.toDate());

		final BillingStatementLogic calculator = mock(BillingStatementLogic.class);
		doAnswer(new Answer<Object>(){
			public Object answer(InvocationOnMock invocation){
				System.out.println("BillingStatementLogic.createBillingStatement called.");
				return null;
			}
		}).when(calculator).createBillingStatement(any(Loan.class));
		
		final BillingStatementRepository billingStatementRepository = mock(BillingStatementRepository.class);
		when(billingStatementRepository.findMostRecentBillingStatementForLoan(1L)).thenReturn(newStatement);

		ApplicationContext springContext = mock(ApplicationContext.class);
		when(springContext.getBean(BillingStatementLogic.class)).thenAnswer(new Answer<BillingStatementLogic>(){
			public BillingStatementLogic answer(InvocationOnMock invocation){
				System.out.println("returning the Mocked BillingStatementLogic class: " + calculator);
				return calculator;
			}
		});
		when(springContext.getBean(BillingStatementRepository.class)).thenAnswer(new Answer<BillingStatementRepository>(){
			public BillingStatementRepository answer(InvocationOnMock invocation){
				System.out.println("returning the Mocked BillingStatementRepository class: " + billingStatementRepository);
				return billingStatementRepository;
			}
		});
		
		KnowledgeBuilder builder= KnowledgeBuilderFactory.newKnowledgeBuilder();
		File ruleFile = ResourceUtils.getFile("classpath:rules/loan.package");
		builder.add(ResourceFactory.newFileResource(ruleFile), ResourceType.DRL);
		ruleFile = ResourceUtils.getFile("classpath:rules/BillingRules.drl");
		builder.add(ResourceFactory.newFileResource(ruleFile), ResourceType.DRL);
		if(builder.hasErrors())
			System.out.println(builder.getErrors());
		assertFalse("Errors loading DRL.", builder.hasErrors());
		KnowledgeBase base = KnowledgeBaseFactory.newKnowledgeBase();
		base.addKnowledgePackages(builder.getKnowledgePackages());
		
		StatelessKnowledgeSession session = base.newStatelessKnowledgeSession();
		session.setGlobal("springContext", springContext);
		KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(session);
		
		DateTime jodaSysDate = new DateTime(new Date().getTime());
		jodaSysDate = jodaSysDate.minusDays(0);
		
		HashMap<String, Date> sysDate = new HashMap<String, Date>();
		sysDate.put("systemDate", jodaSysDate.toDate());
		
		session.execute(Arrays.asList(new Object[]{lastStatement, sysDate,ltp, loan}));
		logger.close();
	}

}
