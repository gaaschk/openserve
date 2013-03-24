package org.gsoft.openserv.web.accountsummary.converter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.service.AccountSummaryService;
import org.gsoft.openserv.web.accountsummary.model.AccountSummaryModel;
import org.gsoft.openserv.web.accountsummary.model.LoanSummaryModel;
import org.gsoft.openserv.web.accountsummary.model.PaymentHistoryModel;
import org.gsoft.openserv.web.accountsummary.model.PaymentModel;
import org.gsoft.openserv.web.person.model.PersonModel;
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
		PaymentHistoryModel paymentHistoryModel = new PaymentHistoryModel();
		ArrayList<PaymentModel> paymentModels = new ArrayList<>();
		for(Payment payment : payments){
			paymentModels.add(conversionService.convert(payment, PaymentModel.class));
		}
		paymentHistoryModel.setPayments(paymentModels);
		accountSummaryModel.setPaymentHistory(paymentHistoryModel);
		return accountSummaryModel;
	}

}
