package org.gsoft.phoenix.web.controller.addloan;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.loan.LoanType;
import org.gsoft.phoenix.service.LoanEntryService;
import org.gsoft.phoenix.service.PersonService;
import org.gsoft.phoenix.service.domain.LoanEntryDocument;
import org.gsoft.phoenix.web.controller.addloan.model.LoanEntryModel;
import org.gsoft.phoenix.web.controller.addloan.model.LoanEntryModelConverter;
import org.gsoft.phoenix.web.controller.addloan.model.PersonModel;
import org.gsoft.phoenix.web.controller.addloan.model.PersonModelConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"loanModel","ssn"})
public class AddLoanController {
	@Resource
	private PersonService personService;
	@Resource
	private LoanEntryService loanEntryService;
	@Resource
	private PersonModelConverter personModelConverter;
	@Resource
	private LoanEntryModelConverter loanModelConverter;
	
	@RequestMapping(value="addloan/index.do", method=RequestMethod.GET)
	public String loadMainAddLoanPage(Model model){
		PersonModel person = new PersonModel();
		model.addAttribute("personModel", person);
		return "addloan/index";
	}
	
	@RequestMapping(value="addloan/index.do", method=RequestMethod.POST)
	public ModelAndView findPersonBySSN(@ModelAttribute("personModel") PersonModel personModel, ModelAndView model){
		Person person = personService.findPersonBySSN(personModel.getSsn());
		PersonModel newPersonModel = personModelConverter.convertToModel(person);
		newPersonModel.setSsn(personModel.getSsn());
		LoanEntryModel loanModel = new LoanEntryModel();
		loanModel.setPerson(newPersonModel);
		model.addObject("loanModel", loanModel);
		model.setViewName("redirect:enterloan.do");
		return model;
	}
	
	@RequestMapping(value="addloan/enterloan.do", method=RequestMethod.GET)
	public ModelAndView loanEnterLoan(@ModelAttribute("loanModel") LoanEntryModel loanModel, ModelAndView model){
		model.addObject("loanModel", loanModel);
		model.setViewName("addloan/enterloan");
		return model;
	}
	
	@RequestMapping(value="addloan/enterloan.do", method=RequestMethod.POST)
	public ModelAndView saveLoan(@ModelAttribute("loanModel") LoanEntryModel loanModel, ModelAndView model){
		System.out.println("In the save loan method.");
		LoanEntryDocument newLoanDoc = loanModelConverter.convertFromModel(loanModel);
		Long loanID = loanEntryService.addNewLoan(newLoanDoc);
		loanModel.setLoanID(loanID);
		Person person = personService.findPersonBySSN(loanModel.getPerson().getSsn());
		loanModel.getPerson().setPersonID(person.getPersonID());
		model.addObject("loanModel", loanModel);
		System.out.println("Loan Saved. [loanID="+loanID+", personID="+person.getPersonID()+"]");
		model.addObject("ssn", person.getSsn());
		model.setViewName("redirect:../accountsummary/home.do");
		return model;
	}
}
