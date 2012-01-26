package org.gsoft.phoenix.web.controller.accountsummary;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.payment.Payment;
import org.gsoft.phoenix.service.AccountSummaryService;
import org.gsoft.phoenix.service.PersonService;
import org.gsoft.phoenix.web.models.AccountSummaryModel;
import org.gsoft.phoenix.web.models.LoanDetailModel;
import org.gsoft.phoenix.web.models.LoanSummaryModel;
import org.gsoft.phoenix.web.models.PaymentHistoryModel;
import org.gsoft.phoenix.web.models.PaymentModel;
import org.gsoft.phoenix.web.models.PersonModel;
import org.gsoft.phoenix.web.person.PersonSearchCriteria;
import org.springframework.core.convert.ConversionService;
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
	private ConversionService conversionService;
	
	@RequestMapping(value="accountsummary/home.do", method=RequestMethod.GET)
	public ModelAndView loadPersonSearch(ModelAndView model){
		model.addObject("personSearchCriteria", new PersonSearchCriteria());
		model.setViewName("accountsummary/personsearch");
		return model;
	}
	
	@RequestMapping(value="accountsummary/home.do", method=RequestMethod.POST)
	public ModelAndView findPerson(@ModelAttribute("personSearchCriteria") PersonSearchCriteria personSearchCriteria, ModelAndView model){
		return loadAccount(personSearchCriteria.getSsn(), model);
	}

	@RequestMapping(value="accountsummary/accountsummary.do", method=RequestMethod.GET)
	public ModelAndView loadAccount(@ModelAttribute("ssn") String ssn, ModelAndView model){
		AccountSummaryModel accountSummaryModel = new AccountSummaryModel();
		Person borrower = personService.findPersonBySSN(ssn);
		PersonModel personModel = conversionService.convert(borrower, PersonModel.class);
		accountSummaryModel.setBorrower(personModel);
		List<Loan> loans = accountSummaryService.getAllLoansForBorrower(borrower.getPersonID());
		accountSummaryModel.setLoans(new ArrayList<LoanSummaryModel>());
		for(Loan loan:loans){
			accountSummaryModel.getLoans().add(conversionService.convert(loan,LoanSummaryModel.class));
		}
		model.addObject("accountmodel", accountSummaryModel);
		List<Payment> payments = accountSummaryService.getAllPaymentsforBorrower(borrower.getPersonID());
		PaymentHistoryModel paymentHistoryModel = conversionService.convert(payments, PaymentHistoryModel.class);
		model.addObject("paymenthistorymodel", paymentHistoryModel);
		model.setViewName("accountsummary/accountsummary");
		return model;
	}
	
	@RequestMapping(value="accountsummary/loandetail.do", method=RequestMethod.GET)
	public ModelAndView showLoanDetail(@RequestParam("loandetailid") String loanid, ModelAndView model){
		Loan theLoan = accountSummaryService.getLoanByID(new Long(loanid));
		LoanDetailModel loanDetailModel = conversionService.convert(theLoan, LoanDetailModel.class);
		model.addObject("loandetailmodel", loanDetailModel);
		model.setViewName("accountsummary/loandetail");
		return model;
	}

	@RequestMapping(value="accountsummary/paymentdetail.do", method=RequestMethod.GET)
	public ModelAndView showPaymentDetail(@RequestParam("paymentdetailid") String paymentid, ModelAndView model){
		Payment thePayment = accountSummaryService.getPaymentByPaymentID(new Long(paymentid));
		PaymentModel paymentModel = conversionService.convert(thePayment, PaymentModel.class);
		model.addObject("selectedpayment", paymentModel);
		model.setViewName("accountsummary/paymentdetail");
		return model;
	}
}
