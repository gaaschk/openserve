package org.gsoft.phoenix.web.person;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonSearchCriteriaValidator implements Validator{
	
	private void doValidate(PersonSearchCriteria criteria, Object context){
		if(criteria == null){
			this.addMessage(context, "ssn", "error.ssn.notnull", "SSN cannot be null.");
		}
		else if(criteria.getSsn().length() != 9){
			this.addMessage(context, "ssn", "error.ssn.incorrectlength", "SSN must be 9 digits.");
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

	private void addMessage(Object validationContext, String source, String messageCode, String defaultMessage){
		if(validationContext instanceof ValidationContext)
			((ValidationContext)validationContext).getMessageContext().addMessage(new MessageBuilder().error().source(source).code(messageCode).defaultText(defaultMessage).build());
		else if(validationContext instanceof Errors)
			((Errors)validationContext).rejectValue(source, messageCode, defaultMessage);
	}
}
