package org.gsoft.phoenix.web.controller.payment;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.service.PersonService;
import org.gsoft.phoenix.service.payment.PaymentService;
import org.gsoft.phoenix.web.models.PaymentEntryModel;
import org.gsoft.phoenix.web.models.PersonModel;
import org.gsoft.phoenix.web.person.PersonSearchCriteria;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class PaymentEntryFlowController {
	@Resource
	private PersonService personService;
	@Resource
	private ConversionService conversionService;
	@Resource
	private PaymentService paymentService;
	
	public PaymentEntryModel findBorrowerAndCreatePaymentEntryModel(PersonSearchCriteria searchCriteria){
		Person person = personService.findPersonBySSN(searchCriteria.getSsn());
		PersonModel newPersonModel = conversionService.convert(person, PersonModel.class);
		newPersonModel.setSsn(searchCriteria.getSsn());
		PaymentEntryModel paymentModel = new PaymentEntryModel();
		paymentModel.setTheBorrower(newPersonModel);
		return paymentModel;
	}
	
	public void submitPayment(PaymentEntryModel paymentModel){
		paymentService.applyPayment(paymentModel.getTheBorrower().getPersonID(), paymentModel.getPaymentAmount(), paymentModel.getPaymentEffectiveDate());
	}
}
