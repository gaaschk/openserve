package org.gsoft.phoenix.buslogic.disclosure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.interest.InterestAccrualLogic;
import org.gsoft.phoenix.domain.disclosure.Disclosure;
import org.gsoft.phoenix.domain.disclosure.DisclosurePayment;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.disclosure.DisclosureRepository;
import org.springframework.stereotype.Component;

@Component
public class DisclosureLogic {
	@Resource
	private PaymentAmountCalculator paymentCalculator;
	@Resource
	private InterestAccrualLogic interestLogic;
	@Resource
	private DisclosureRepository disclosureRepository;
	
	
	public Disclosure createDisclosure(Loan loan){
		BigDecimal annualInterestRate = interestLogic.getInterestRateForLoan(loan);
		Integer paymentAmount = paymentCalculator.calculatePaymentAmount(loan.getCurrentPrincipal(), annualInterestRate, loan.getRemainingLoanTerm());
		int regularPaymentCount = loan.getCurrentPrincipal()/paymentAmount;
		int lastPaymentAmount = loan.getCurrentPrincipal() - (paymentAmount*regularPaymentCount);
		Disclosure disclosure = new Disclosure();
		disclosure.setDisclosureDate(new Date());
		disclosure.setLoanID(loan.getLoanID());
		disclosure.setEffectiveDate(new Date());
		disclosure.setDisclosurePayments(new ArrayList<DisclosurePayment>());
		DisclosurePayment regularPayment = new DisclosurePayment();
		regularPayment.setPaymentAmount(paymentAmount);
		regularPayment.setPaymentCount(regularPaymentCount);
		disclosure.getDisclosurePayments().add(regularPayment);
		DisclosurePayment lastPayment = new DisclosurePayment();
		lastPayment.setPaymentAmount(lastPaymentAmount);
		lastPayment.setPaymentCount(1);
		disclosureRepository.save(disclosure);
		return disclosure;
	}
}
