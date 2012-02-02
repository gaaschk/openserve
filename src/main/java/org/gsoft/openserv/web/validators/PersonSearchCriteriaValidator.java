package org.gsoft.openserv.web.validators;

import org.gsoft.openserv.web.person.PersonSearchCriteria;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonSearchCriteriaValidator extends BaseValidator implements Validator{
	
	private void doValidate(PersonSearchCriteria criteria, Object context){
		if(criteria == null){
			this.addMessage(context, "ssn", "error.empty.ssn");
		}
		else if(criteria.getSsn().length() != 9){
			this.addMessage(context, "ssn", "error.length.ssn");
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PersonSearchCriteria.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		this.doValidate((PersonSearchCriteria)target, errors);
	}
	
	public void validate(PersonSearchCriteria criteria, ValidationContext validationContext) {
		this.doValidate(criteria, validationContext);
	}

}
