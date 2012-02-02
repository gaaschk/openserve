package org.gsoft.openserv.util;

import org.gsoft.openserv.domain.Person;
import org.springframework.stereotype.Repository;

@Repository
public class PersonFactory {
	
	public Person getPersonByID(int testPersonID){
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setSsn("000000001");
		return person;
	}
}
