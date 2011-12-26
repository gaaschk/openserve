package org.gsoft.phoenix.service;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.loan.LoanLookupLogic;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.domain.payment.Payment;
import org.gsoft.phoenix.repositories.loan.LoanEventRepository;
import org.gsoft.phoenix.repositories.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class AccountSummaryService {
	@Resource
	private LoanLookupLogic loanLookupLogic;
	@Resource
	private LoanEventRepository loanEventRepository;
	@Resource
	private PaymentRepository paymentRepository;
	
	public List<Loan> getAllLoansForBorrower(Long borrowerID){
		return loanLookupLogic.getAllLoansForBorrower(borrowerID);
	}
	
	public Loan getLoanByID(Long loanID){
		return loanLookupLogic.getLoanByID(loanID);
	}
	
	public List<LoanEvent> getAllLoanEventsForLoan(Long loanID){
		return loanEventRepository.findAllByLoanID(loanID);
	}
	
	public List<Payment> getAllPaymentsforBorrower(Long borrowerID){
		return paymentRepository.findAllPaymentsByBorrowerPersonID(borrowerID);
	}
}
