package org.gsoft.openserv.web.converters;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.web.models.PersonModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonToPersonModelConverter implements Converter<Person, PersonModel>{
	
	public PersonModel convert(Person person){
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