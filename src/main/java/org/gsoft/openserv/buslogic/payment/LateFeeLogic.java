package org.gsoft.openserv.buslogic.payment;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.payment.LateFee;
import org.gsoft.openserv.domain.payment.billing.BillingStatement;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.gsoft.openserv.repositories.payment.BillingStatementRepository;
import org.gsoft.openserv.repositories.payment.LateFeeRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class LateFeeLogic {
	@Resource
	private BillingStatementRepository billingStatementRepository;
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	@Resource
	private SystemSettingsLogic systemSettings;
	@Resource
	private LateFeeRepository lateFeeRepository;
	
	public void updateLateFees(Loan loan){
//		LoanTypeProfile ltp = loanTypeProfileRepository.findOne(loan.getEffectiveLoanTypeProfileID());
//		List<BillingStatement> bills = billingStatementRepository.findAllBillsForLoan(loan.getLoanID());
//		for(BillingStatement bill:bills){
//			Date dueDateWithLateGrace = new DateTime(bill.getDueDate()).plusDays(ltp.getDaysLateForFee()).toDate();
//			if(((bill.getSatisfiedDate() == null && dueDateWithLateGrace.before(systemSettings.getCurrentSystemDate())) ||
//					(bill.getSatisfiedDate() != null && dueDateWithLateGrace.before(bill.getSatisfiedDate()))) && 
//					(bill.getLateFee() == null || false/*bill.getLateFee().isCancelled()*/)){
//				//assess late fee
//				this.assessLateFee(bill, ltp, loan);
//			}
//			else if(bill.getLateFee() != null && true/*!bill.getLateFee().isCancelled()*/ && 
//					(dueDateWithLateGrace.after(systemSettings.getCurrentSystemDate()) || 
//					(bill.getSatisfiedDate() != null && dueDateWithLateGrace.after(bill.getSatisfiedDate())))){
//				//cancel late fee
//				this.cancelLateFee(bill, loan);
//			}
//		}
	}
	
	private void assessLateFee(BillingStatement bill, LoanTypeProfile ltp, Loan loan){
		//redundant check, but better safe than sorry.
//		if(bill.getLateFee() == null){
//			LateFee lateFee = new LateFee();
//			lateFee = lateFeeRepository.save(lateFee);
//			bill.setLateFee(lateFee);
//			lateFee.setFeeAmount(ltp.getLateFeeAmount());
//			Date dueDateWithLateGrace = new DateTime(bill.getDueDate()).plusDays(ltp.getDaysLateForFee()).toDate();
//		}
	}
	
	private void cancelLateFee(BillingStatement bill, Loan loan){
//		if(bill.getLateFee() != null){
//			LateFee lateFee = bill.getLateFee();
//		}
	}
}
