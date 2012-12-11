package org.gsoft.openserv.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.gsoft.openserv.Authenticator;
import org.gsoft.openserv.data.DatabaseUtility;
import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-context.xml")
public class PersonServiceIT{
	@Resource
	private PersonService personService;
	@Resource
	private DatabaseUtility dbutility;
	@Resource
	private Authenticator authenticator;
	
	@Before
	public void refreshDatabase() throws IOException{
		dbutility.refreshDatabase();
	}

	@Test
	public void test() {
		authenticator.authenticate();
		Person borrower = new Person();
		borrower.setSsn("000000000");
		borrower.setFirstName("John");
		borrower.setLastName("Doe");
		borrower = personService.savePerson(borrower);
	}

}
