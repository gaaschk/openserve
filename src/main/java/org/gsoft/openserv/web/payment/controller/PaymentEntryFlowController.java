package org.gsoft.openserv.web.payment.controller;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.service.PersonService;
import org.gsoft.openserv.service.payment.PaymentService;
import org.gsoft.openserv.web.payment.model.PaymentEntryModel;
import org.gsoft.openserv.web.person.PersonSearchCriteria;
import org.gsoft.openserv.web.person.model.PersonModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class PaymentEntryFlowController {
	@Resource
	private PersonService personService;
	@Resource(name="customConversionService")
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
		paymentService.applyPayment(paymentModel.getTheBorrower().getPersonID(), paymentModel.getPaymentAmount()*-1, paymentModel.getPaymentEffectiveDate());
	}
}
