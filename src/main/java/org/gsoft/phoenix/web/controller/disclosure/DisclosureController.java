package org.gsoft.phoenix.web.controller.disclosure;

import org.gsoft.phoenix.web.controller.addloan.model.PersonModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DisclosureController {

	@RequestMapping(value="disclosure/home.do", method=RequestMethod.GET)
	public ModelAndView loadPersonSearch(ModelAndView model){
		model.addObject("personModel", new PersonModel());
		model.setViewName("accountsummary/personsearch");
		return model;
	}
}
