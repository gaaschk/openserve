package org.gsoft.phoenix.service;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.loan.LoanLookupLogic;
import org.gsoft.phoenix.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.domain.payment.Payment;
import org.gsoft.phoenix.repositories.amortization.AmortizationScheduleRepository;
import org.gsoft.phoenix.repositories.loan.LoanEventRepository;
import org.gsoft.phoenix.repositories.payment.PaymentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@Resource
	private AmortizationScheduleRepository amortizationRepository;
	
	@PreAuthorize("hasRole('PERM_ViewAccountSummary')")
	public List<Loan> getAllLoansForBorrower(Long borrowerID){
		return loanLookupLogic.getAllLoansForBorrower(borrowerID);
	}
	
	@PreAuthorize("hasRole('PERM_ViewAccountSummary')")
	public Loan getLoanByID(Long loanID){
		return loanLookupLogic.getLoanByID(loanID);
	}
	
	@PreAuthorize("hasRole('PERM_ViewAccountSummary')")
	public List<LoanEvent> getAllLoanEventsForLoan(Long loanID){
		return loanEventRepository.findAllByLoanID(loanID);
	}
	
	@PreAuthorize("hasRole('PERM_ViewAccountSummary')")
	public List<Payment> getAllPaymentsforBorrower(Long borrowerID){
		return paymentRepository.findAllPaymentsByBorrowerPersonID(borrowerID);
	}

	@PreAuthorize("hasRole('PERM_ViewAccountSummary')")
	public Payment getPaymentByPaymentID(Long paymentID){
		Payment payment = paymentRepository.findOne(paymentID);
		return payment;
	}
	
	@PreAuthorize("hasRole('PERM_ViewAccountSummary')")
	public LoanAmortizationSchedule getAmortizationScheduleForLoan(Long loanID){
		return amortizationRepository.findScheduleForLoan(loanID);
	}
}
