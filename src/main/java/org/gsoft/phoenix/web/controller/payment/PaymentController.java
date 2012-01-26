package org.gsoft.phoenix.web.controller.payment;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.service.PersonService;
import org.gsoft.phoenix.service.payment.PaymentService;
import org.gsoft.phoenix.web.models.PaymentEntryModel;
import org.gsoft.phoenix.web.models.PersonModel;
import org.gsoft.phoenix.web.person.PersonSearchCriteria;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"ssn", "paymentModel"})
public class PaymentController {
	@Resource
	private PersonService personService;
	@Resource
	private ConversionService conversionService;
	@Resource
	private PaymentService paymentService;
	
	@RequestMapping(value="payment/search.do", method=RequestMethod.GET)
	public String loadMainAddLoanPage(Model model){
		PersonSearchCriteria person = new PersonSearchCriteria();
		model.addAttribute("personSearchCriteria", person);
		return "payment/search";
	}
	
	@RequestMapping(value="payment/search.do", method=RequestMethod.POST)
	public ModelAndView findBorrowerAndLoadPaymentPage(@ModelAttribute("personSearchCriteria") PersonSearchCriteria personSearchCriteria, ModelAndView model){
		Person person = personService.findPersonBySSN(personSearchCriteria.getSsn());
		PersonModel newPersonModel = conversionService.convert(person, PersonModel.class);
		newPersonModel.setSsn(personSearchCriteria.getSsn());
		PaymentEntryModel paymentModel = new PaymentEntryModel();
		paymentModel.setTheBorrower(newPersonModel);
		model.addObject("paymentModel", paymentModel);
		model.setViewName("redirect:makepayment.do");
		return model;
	}
	
	@RequestMapping(value="payment/makepayment.do", method=RequestMethod.GET)
	public ModelAndView loadPaymentView(@ModelAttribute("paymentModel") PaymentEntryModel paymentModel, ModelAndView model){
		model.addObject("paymentModel", paymentModel);
		model.setViewName("payment/makepayment");
		return model;
	}

	@RequestMapping(value="payment/makepayment.do", method=RequestMethod.POST)
	public ModelAndView findBorrower(@ModelAttribute("paymentModel") PaymentEntryModel paymentModel, ModelAndView model){
		paymentService.applyPayment(paymentModel.getTheBorrower().getPersonID(), paymentModel.getPaymentAmount(), new Date());
		model.addObject("ssn", paymentModel.getTheBorrower().getSsn());
		model.setViewName("redirect:../accountsummary/home.do");
		return model;
	}
	
}
