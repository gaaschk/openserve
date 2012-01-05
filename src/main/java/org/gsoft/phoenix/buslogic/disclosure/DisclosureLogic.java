package org.gsoft.phoenix.buslogic.disclosure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.interest.InterestAccrualLogic;
import org.gsoft.phoenix.domain.disclosure.LoanDisclosure;
import org.gsoft.phoenix.domain.disclosure.LoanDisclosurePayment;
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
	
	
	public LoanDisclosure createDisclosure(Loan loan){
		BigDecimal annualInterestRate = interestLogic.getInterestRateForLoan(loan);
		Integer paymentAmount = paymentCalculator.calculatePaymentAmount(loan.getCurrentPrincipal(), annualInterestRate, loan.getRemainingLoanTerm());
		int regularPaymentCount = loan.getCurrentPrincipal()/paymentAmount;
		int lastPaymentAmount = loan.getCurrentPrincipal() - (paymentAmount*regularPaymentCount);
		LoanDisclosure disclosure = new LoanDisclosure();
		disclosure.setDisclosureDate(new Date());
		disclosure.setLoanID(loan.getLoanID());
		disclosure.setEffectiveDate(new Date());
		disclosure.setDisclosurePayments(new ArrayList<LoanDisclosurePayment>());
		LoanDisclosurePayment regularPayment = new LoanDisclosurePayment();
		regularPayment.setPaymentAmount(paymentAmount);
		regularPayment.setPaymentCount(regularPaymentCount);
		disclosure.getDisclosurePayments().add(regularPayment);
		LoanDisclosurePayment lastPayment = new LoanDisclosurePayment();
		lastPayment.setPaymentAmount(lastPaymentAmount);
		lastPayment.setPaymentCount(1);
		disclosureRepository.save(disclosure);
		return disclosure;
	}
}
