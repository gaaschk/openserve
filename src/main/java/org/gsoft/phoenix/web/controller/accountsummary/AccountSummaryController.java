package org.gsoft.phoenix.web.controller.accountsummary;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Loan;
import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.service.AccountSummaryService;
import org.gsoft.phoenix.service.PersonService;
import org.gsoft.phoenix.web.controller.accountsummary.model.AccountSummaryModel;
import org.gsoft.phoenix.web.controller.accountsummary.model.LoanDetailModel;
import org.gsoft.phoenix.web.controller.accountsummary.model.LoanDetailModelConverter;
import org.gsoft.phoenix.web.controller.accountsummary.model.LoanSummaryModel;
import org.gsoft.phoenix.web.controller.accountsummary.model.LoanSummaryModelConverter;
import org.gsoft.phoenix.web.controller.addloan.model.PersonModel;
import org.gsoft.phoenix.web.controller.addloan.model.PersonModelConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"ssn"})
public class AccountSummaryController {
	@Resource
	private PersonService personService;
	@Resource
	private AccountSummaryService accountSummaryService;
	@Resource
	private PersonModelConverter personModelConverter;
	@Resource
	private LoanSummaryModelConverter loanSummaryModelConverter;
	@Resource
	private LoanDetailModelConverter loanDetailModelConverter;
	
	@RequestMapping(value="accountsummary/home.do", method=RequestMethod.GET)
	public ModelAndView loadPersonSearch(ModelAndView model){
		model.addObject("personModel", new PersonModel());
		model.setViewName("accountsummary/personsearch");
		return model;
	}
	
	@RequestMapping(value="accountsummary/home.do", method=RequestMethod.POST)
	public ModelAndView findPerson(@ModelAttribute("personModel") PersonModel person, ModelAndView model){
		return loadAccount(person.getSsn(), model);
	}

	@RequestMapping(value="accountsummary/accountsummary.do", method=RequestMethod.GET)
	public ModelAndView loadAccount(@ModelAttribute("ssn") String ssn, ModelAndView model){
		AccountSummaryModel accountSummaryModel = new AccountSummaryModel();
		Person borrower = personService.findPersonBySSN(ssn);
		PersonModel personModel = personModelConverter.convertToModel(borrower);
		accountSummaryModel.setBorrower(personModel);
		List<Loan> loans = accountSummaryService.getAllLoansForBorrower(borrower.getPersonID());
		accountSummaryModel.setLoans(new ArrayList<LoanSummaryModel>());
		for(Loan loan:loans){
			accountSummaryModel.getLoans().add(loanSummaryModelConverter.convertToModel(loan));
		}
		model.addObject("accountmodel", accountSummaryModel);
		model.setViewName("accountsummary/accountsummary");
		return model;
	}
	
	@RequestMapping(value="accountsummary/loandetail.do", method=RequestMethod.GET)
	public ModelAndView showLoanDetail(@RequestParam("loandetailid") String loanid, ModelAndView model){
		Loan theLoan = accountSummaryService.getLoanByID(new Long(loanid));
		LoanDetailModel loanDetailModel = loanDetailModelConverter.convertToModel(theLoan);
		model.addObject("loandetailmodel", loanDetailModel);
		model.setViewName("accountsummary/loandetail");
		return model;
	}
}
