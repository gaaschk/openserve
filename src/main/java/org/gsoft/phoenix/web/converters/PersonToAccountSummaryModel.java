package org.gsoft.phoenix.web.converters;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.payment.Payment;
import org.gsoft.phoenix.service.AccountSummaryService;
import org.gsoft.phoenix.web.models.AccountSummaryModel;
import org.gsoft.phoenix.web.models.LoanSummaryModel;
import org.gsoft.phoenix.web.models.PaymentHistoryModel;
import org.gsoft.phoenix.web.models.PersonModel;
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
