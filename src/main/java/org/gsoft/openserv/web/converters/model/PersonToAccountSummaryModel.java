package org.gsoft.openserv.web.converters.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.service.AccountSummaryService;
import org.gsoft.openserv.web.models.AccountSummaryModel;
import org.gsoft.openserv.web.models.LoanSummaryModel;
import org.gsoft.openserv.web.models.PaymentHistoryModel;
import org.gsoft.openserv.web.models.PersonModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonToAccountSummaryModel implements Converter<Person,AccountSummaryModel>{
	@Resource
	private AccountSummaryService accountSummaryService;
	@Resource
	private ConversionService conversionService;
	
	@Override
	public AccountSummaryModel convert(Person borrower) {
		AccountSummaryModel accountSummaryModel = new AccountSummaryModel();
		PersonModel personModel = conversionService.convert(borrower, PersonModel.class);
		accountSummaryModel.setBorrower(personModel);
		List<Loan> loans = accountSummaryService.getAllLoansForBorrower(borrower.getPersonID());
		accountSummaryModel.setLoans(new ArrayList<LoanSummaryModel>());
		for(Loan loan:loans){
			accountSummaryModel.getLoans().add(conversionService.convert(loan,LoanSummaryModel.class));
		}
		List<Payment> payments = accountSummaryService.getAllPaymentsforBorrower(borrower.getPersonID());
		PaymentHistoryModel paymentHistoryModel = conversionService.convert(payments, PaymentHistoryModel.class);
		accountSummaryModel.setPaymentHistory(paymentHistoryModel);
		return accountSummaryModel;
	}

}
