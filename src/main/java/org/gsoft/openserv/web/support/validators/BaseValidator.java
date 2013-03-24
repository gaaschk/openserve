package org.gsoft.openserv.web.support.validators;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.validation.Errors;

public abstract class BaseValidator{
	protected void addMessage(Object validationContext, String source, String messageCode){
		if(validationContext instanceof ValidationContext)
			((ValidationContext)validationContext).getMessageContext().addMessage(new MessageBuilder().error().source(source).code(messageCode).defaultText("Message code not found. [" + messageCode + "]").build());
		else if(validationContext instanceof Errors)
			((Errors)validationContext).rejectValue(source, messageCode, "Message code not found. [" + messageCode + "]");
	}

}
