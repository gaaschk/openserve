package org.gsoft.phoenix.service;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.MaintainPersonLogic;
import org.gsoft.phoenix.domain.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class PersonService {
	@Resource
	private MaintainPersonLogic personLogic;
	
	public Person findPersonBySSN(String ssn){
		return personLogic.findPersonBySsn(ssn);
	}
	
	@Transactional
	public Person savePerson(Person person){
		return personLogic.savePerson(person);
	}
}
