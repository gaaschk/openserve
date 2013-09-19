package org.gsoft.openserv.repositories.amortization;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.amortization.AmortizationSchedule;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeFieldType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-context.xml")
@Transactional(readOnly=true)
public class AmortizationScheduleRepositoryTest {
	//la ti da ti daaaaaaaa
	
	@Resource
	private AmortizationScheduleRepository amortizationRepository;
	
	@Test
	@Rollback
	public void test() {
		Date today = new Date();
		AmortizationSchedule asched = new AmortizationSchedule();
		asched.setCreationDate(today);
		asched.setEffectiveDate(today);
		asched = amortizationRepository.save(asched);
		asched = amortizationRepository.findOne(asched.getAmortizationScheduleID());
		DateTimeComparator comparator = DateTimeComparator.getInstance(DateTimeFieldType.secondOfDay());
		assertNotNull("Expected generation of primary key", asched.getAmortizationScheduleID());
		assertTrue("Expected creation date to be " + today + " but was " + asched.getCreationDate(), comparator.compare(today, asched.getCreationDate()) == 0);
		assertTrue("Expected effective date to be " + today + " but was " + asched.getEffectiveDate(), comparator.compare(today, asched.getEffectiveDate()) == 0);
	}

}
