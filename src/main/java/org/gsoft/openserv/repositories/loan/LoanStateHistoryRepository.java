package org.gsoft.openserv.repositories.loan;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.gsoft.openserv.repositories.LoanRateValueRepository;
import org.gsoft.openserv.repositories.payment.LoanPaymentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LoanStateHistoryRepository {
	@Resource
	private LoanRepository loanRepo;
	@Resource
	private LoanPaymentRepository paymentRepo;
	@Resource
	private LoanBalanceAdjustmentRepository loanBalanceAdjustmentRepo;
	@Resource
	private LoanRateValueRepository loanRateValueRepo;
	
	public LoanStateHistory findLoanStateHistory(Long loanID){
		Loan loan = loanRepo.findOne(loanID);
		return this.findLoanStateHistory(loan);
	}

	public LoanStateHistory findLoanStateHistory(Loan loan){
		LoanStateHistory loanStateHistory = new LoanStateHistory();
		loanStateHistory.addDisbursements(loan.getDisbursements());
		loanStateHistory.addAllRateChanges(loanRateValueRepo.findAllLoanRateValues(loan.getLoanID()));
		loanStateHistory.addAllAdjustments(loanBalanceAdjustmentRepo.findAllLoanBalanceAdjustmentsForLoan(loan.getLoanID()));
		loanStateHistory.addAllPayments(paymentRepo.findAllLoanPayments(loan.getLoanID()));
		return loanStateHistory;
	}
}
