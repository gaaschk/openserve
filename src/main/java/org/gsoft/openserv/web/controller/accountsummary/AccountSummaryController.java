package org.gsoft.openserv.web.controller.accountsummary;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.service.AccountSummaryService;
import org.gsoft.openserv.service.PersonService;
import org.gsoft.openserv.web.models.AccountSummaryModel;
import org.gsoft.openserv.web.models.LoanDetailModel;
import org.gsoft.openserv.web.models.PaymentModel;
import org.gsoft.openserv.web.person.PersonSearchCriteria;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("accountsummary")
public class AccountSummaryController {
	@Resource
	private PersonService personService;
	@Resource
	private AccountSummaryService accountSummaryService;
	@Resource
	private ConversionService conversionService;

	public AccountSummaryModel loadAccount(PersonSearchCriteria personSearchCriteria){
		Person borrower = personService.findPersonBySSN(personSearchCriteria.getSsn());
		return conversionService.convert(borrower, AccountSummaryModel.class);
	}
	
	@RequestMapping(value="/loandetail.do", method=RequestMethod.GET)
	public ModelAndView showLoanDetail(@RequestParam("loandetailid") String loanid, ModelAndView model){
		Loan theLoan = accountSummaryService.getLoanByID(new Long(loanid));
		LoanDetailModel loanDetailModel = conversionService.convert(theLoan, LoanDetailModel.class);
		model.addObject("loandetailmodel", loanDetailModel);
		return model;
	}

	@RequestMapping(value="/loanfinancialdata.do", method=RequestMethod.GET)
	public ModelAndView showLoanFinancialData(@RequestParam("loandetailid") String loanid, ModelAndView model){
		Loan theLoan = accountSummaryService.getLoanByID(new Long(loanid));
		LoanDetailModel loanDetailModel = conversionService.convert(theLoan, LoanDetailModel.class);
		model.addObject("loandetailmodel", loanDetailModel);
		return model;
	}

	@RequestMapping(value="/transactionhistory.do", method=RequestMethod.GET)
	public ModelAndView showTransactionHistory(@RequestParam("loandetailid") String loanid, ModelAndView model){
		Loan theLoan = accountSummaryService.getLoanByID(new Long(loanid));
		LoanDetailModel loanDetailModel = conversionService.convert(theLoan, LoanDetailModel.class);
		model.addObject("loandetailmodel", loanDetailModel);
		return model;
	}

	@RequestMapping(value="/amortizationschedule.do", method=RequestMethod.GET)
	public ModelAndView showAmortizationSchedule(@RequestParam("loandetailid") String loanid, ModelAndView model){
		Loan theLoan = accountSummaryService.getLoanByID(new Long(loanid));
		LoanDetailModel loanDetailModel = conversionService.convert(theLoan, LoanDetailModel.class);
		model.addObject("loandetailmodel", loanDetailModel);
		return model;
	}

	@RequestMapping(value="/billingstatements.do", method=RequestMethod.GET)
	public ModelAndView showBillingStatements(@RequestParam("loandetailid") String loanid, ModelAndView model){
		Loan theLoan = accountSummaryService.getLoanByID(new Long(loanid));
		LoanDetailModel loanDetailModel = conversionService.convert(theLoan, LoanDetailModel.class);
		model.addObject("loandetailmodel", loanDetailModel);
		return model;
	}

	@RequestMapping(value="/paymentdetail.do", method=RequestMethod.GET)
	public ModelAndView showPaymentDetail(@RequestParam("paymentdetailid") String paymentid, ModelAndView model){
		Payment thePayment = accountSummaryService.getPaymentByPaymentID(new Long(paymentid));
		PaymentModel paymentModel = conversionService.convert(thePayment, PaymentModel.class);
		model.addObject("selectedpayment", paymentModel);
		return model;
	}
}
