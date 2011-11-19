package org.gsoft.phoenix.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.gsoft.phoenix.data.DatabaseUtility;
import org.gsoft.phoenix.domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/application-context.xml")
public class TestPersonServiceIT {
	@Resource
	private PersonService personService;
	@Resource
	private DatabaseUtility dbutility;

	@Before
	public void refreshDatabase() throws IOException{
		dbutility.refreshDatabase();
	}

	@Test
	public void test() {
		Person borrower = new Person();
		borrower.setSsn("000000000");
		borrower.setFirstName("John");
		borrower.setLastName("Doe");
		borrower = personService.savePerson(borrower);
	}

}
