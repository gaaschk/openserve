package org.gsoft.phoenix.web.controller.payment;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.service.PersonService;
import org.gsoft.phoenix.service.payment.PaymentService;
import org.gsoft.phoenix.web.controller.addloan.model.PersonModel;
import org.gsoft.phoenix.web.controller.addloan.model.PersonModelConverter;
import org.gsoft.phoenix.web.controller.payment.model.PaymentEntryModel;
import org.gsoft.phoenix.web.person.PersonSearchCriteria;
import org.springframework.stereotype.Service;

@Service
public class PaymentEntryFlowController {
	@Resource
	private PersonService personService;
	@Resource
	private PersonModelConverter personModelConverter;
	@Resource
	private PaymentService paymentService;
	
	public PaymentEntryModel findBorrowerAndCreatePaymentEntryModel(PersonSearchCriteria searchCriteria){
		Person person = personService.findPersonBySSN(searchCriteria.getSsn());
		PersonModel newPersonModel = personModelConverter.convertToModel(person);
		newPersonModel.setSsn(searchCriteria.getSsn());
		PaymentEntryModel paymentModel = new PaymentEntryModel();
		paymentModel.setTheBorrower(newPersonModel);
		return paymentModel;
	}
	
	public void submitPayment(PaymentEntryModel paymentModel){
		paymentService.applyPayment(paymentModel.getTheBorrower().getPersonID(), paymentModel.getPaymentAmount(), new Date());
	}

}
