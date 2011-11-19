package org.gsoft.phoenix.buslogic;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.repositories.PersonRepository;
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
