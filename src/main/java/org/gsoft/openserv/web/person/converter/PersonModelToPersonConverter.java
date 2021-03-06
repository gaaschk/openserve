package org.gsoft.openserv.web.person.converter;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.service.PersonService;
import org.gsoft.openserv.web.person.model.PersonModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonModelToPersonConverter implements Converter<PersonModel, Person>{
	@Resource
	private PersonService personService;
	
	public Person convert(PersonModel model){
		Person person = personService.findPersonBySSN(model.getSsn());
		if(person==null)person=new Person();
		person.setSsn(model.getSsn());
		person.setFirstName(model.getFirstName());
		person.setLastName(model.getLastName());
		return person;
	}
	
}
