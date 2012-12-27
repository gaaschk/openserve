package org.gsoft.openserv.service;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.repositories.amortization.AmortizationScheduleRepository;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.repositories.payment.PaymentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class AccountSummaryService {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private PaymentRepository paymentRepository;
	@Resource
	private AmortizationScheduleRepository amortizationRepository;
	
	@PreAuthorize("hasRole('PERM_ViewAccountSummary')")
	public List<Loan> getAllLoansForBorrower(Long borrowerID){
		return loanRepository.findAllForBorrower(borrowerID);
	}
	
	@PreAuthorize("hasRole('PERM_ViewAccountSummary')")
	public Loan getLoanByID(Long loanID){
		return loanRepository.findOne(loanID);
	}
	
	@PreAuthorize("hasRole('PERM_ViewAccountSummary')")
	public List<?> getAllLoanEventsForLoan(Long loanID){
		return null;
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
