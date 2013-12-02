package org.gsoft.openserv.repositories.amortization;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.gsoft.openserv.config.CoreConfig;
import org.gsoft.openserv.config.PersistenceConfig;
import org.gsoft.openserv.domain.amortization.AmortizationSchedule;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeFieldType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(readOnly=true)
@ContextConfiguration(classes = {PersistenceConfig.class, CoreConfig.class}, loader = SpringApplicationContextLoader.class)
public class AmortizationScheduleRepositoryTest {
	
	@Autowired
	AmortizationScheduleRepository amortizationRepository;
	
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
