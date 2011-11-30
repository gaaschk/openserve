package org.gsoft.phoenix.web.controller.accountsummary;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"ssn"})
public class AccountSummaryController {
	@Resource
	private PersonService personService;
	
	@RequestMapping(value="accountsummary/home.do", method=RequestMethod.GET)
	public ModelAndView loadAccount(@ModelAttribute("ssn") String ssn, ModelAndView model){
		Person borrower = personService.findPersonBySSN(ssn);
		
		return model;
	}
}
