package org.gsoft.openserv.web.person.validator;

import org.gsoft.openserv.web.person.PersonSearchCriteria;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonSearchCriteriaValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PersonSearchCriteria.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		this.doValidate((PersonSearchCriteria)target, errors);
	}
	
	private void doValidate(PersonSearchCriteria criteria, Errors e){
		if(criteria == null){
			e.rejectValue("ssn", "error.empty.ssn");
		}
		else if(criteria.getSsn().length() != 9){
			e.rejectValue("ssn", "error.length.ssn");
		}
	}
}
