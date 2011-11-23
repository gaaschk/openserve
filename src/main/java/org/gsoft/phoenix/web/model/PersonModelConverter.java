package org.gsoft.phoenix.web.model;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.service.PersonService;
import org.springframework.stereotype.Component;

@Component
public class PersonModelConverter {
	@Resource
	private PersonService personService;
	
	public Person convertFromModel(PersonModel model){
		Person person = personService.findPersonBySSN(model.getSsn());
		if(person==null)person=new Person();
		person.setSsn(model.getSsn());
		person.setFirstName(model.getFirstName());
		person.setLastName(model.getLastName());
		return person;
	}
	
	public PersonModel convertToModel(Person person){
		PersonModel model = new PersonModel();
		if(person!=null){
			model.setPersonID(person.getPersonID());
			model.setSsn(person.getSsn());
			model.setFirstName(person.getFirstName());
			model.setLastName(person.getLastName());
		}
		return model;
	}
}
