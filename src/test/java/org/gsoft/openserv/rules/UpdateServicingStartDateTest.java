package org.gsoft.openserv.rules;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.gsoft.openserv.domain.loan.Loan;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

public class UpdateServicingStartDateTest {

	@Test
	public void test() throws IOException {
		KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		File ruleDir = ResourceUtils.getFile("classpath:rules");
		System.out.println("Rule directory " + (ruleDir.exists()?"":"not ") + "found.");
		System.out.println(ruleDir.getCanonicalPath());
		for(File ruleFile:ruleDir.listFiles()){
			System.out.println("Trying file " + ruleFile.getName());
			if(ruleFile.getName().endsWith(".drl"))
				builder.add(ResourceFactory.newFileResource(ruleFile), ResourceType.DRL);
		}
		KnowledgeBase base = KnowledgeBaseFactory.newKnowledgeBase();
		base.addKnowledgePackages(builder.getKnowledgePackages());
		StatelessKnowledgeSession session = base.newStatelessKnowledgeSession();
		Loan loan = new Loan();
		Date sysDate = new Date();
		session.execute(Arrays.asList(new Object[]{loan, sysDate}));
		assertTrue("Expected rule to set servicingStartDate.", loan.getServicingStartDate() != null);
		long serviceStartDateMillis = loan.getServicingStartDate().getTime();
		sysDate = new Date();
		session.execute(Arrays.asList(new Object[]{loan, sysDate}));
		assertTrue("Expected servicingStartDate value to be unchanged on second run.", loan.getServicingStartDate().getTime() == serviceStartDateMillis);
	}

}
