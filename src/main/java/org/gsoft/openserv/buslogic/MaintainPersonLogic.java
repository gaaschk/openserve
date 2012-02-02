package org.gsoft.openserv.buslogic;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.repositories.PersonRepository;
import org.springframework.stereotype.Component;

@Component
public class MaintainPersonLogic {
	@Resource
	private PersonRepository personRepostory;
	
	public Person findPersonBySsn(String ssn){
		return this.personRepostory.findPersonBySsn(ssn);
	}
	
	public Person savePerson(Person person){
		return this.personRepostory.save(person);
	}
}
