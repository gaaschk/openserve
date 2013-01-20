package org.gsoft.openserv.repositories.loan;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Disbursement;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.gsoft.openserv.repositories.payment.LoanPaymentRepository;
import org.gsoft.openserv.repositories.rates.LoanRateValueRepository;
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
	
	public LoanStateHistory findLoanStateHistoryAsOf(Loan loan, Date asOfDate){
		LoanStateHistory loanStateHistory = new LoanStateHistory();
		for(Disbursement disb:loan.getDisbursements()){
			if(!disb.getDisbursementEffectiveDate().after(asOfDate)){
				loanStateHistory.addDisbursement(disb);
			}
		}
		loanStateHistory.addAllRateChanges(loanRateValueRepo.findAllLoanRateValuesThruDate(loan.getLoanID(), asOfDate));
		loanStateHistory.addAllAdjustments(loanBalanceAdjustmentRepo.findAllForLoanThruDate(loan.getLoanID(), asOfDate));
		loanStateHistory.addAllPayments(paymentRepo.findAllLoanPaymentsEffectiveOnOrBefore(loan.getLoanID(), asOfDate));
		return loanStateHistory;
	}
}
