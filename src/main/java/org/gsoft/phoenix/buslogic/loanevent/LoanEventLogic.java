package org.gsoft.phoenix.buslogic.loanevent;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.interest.InterestAccrualLogic;
import org.gsoft.phoenix.buslogic.system.SystemSettingsLogic;
import org.gsoft.phoenix.domain.Loan;
import org.gsoft.phoenix.domain.loan.LoanEvent;
import org.gsoft.phoenix.domain.loan.LoanEventType;
import org.gsoft.phoenix.domain.loan.LoanTransaction;
import org.gsoft.phoenix.repositories.loan.LoanEventRepository;
import org.gsoft.phoenix.repositories.loan.LoanTransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanEventLogic {
	@Resource
	private SystemSettingsLogic systemSettingsLogic;
	@Resource
	private LoanEventRepository loanEventRepository;
	@Resource
	private LoanTransactionRepository loanTransactionRepository;
	@Resource
	private InterestAccrualLogic interestAccrualLogic;
	
	/**
	 * This method will create a loanevent without persisting it.  All of the monetary values 
	 * will be 0 except for the accrued interest which will contain the interest accrued through
	 * the effective date passed in.  This will allow the caller to adjust the amounts to apply 
	 * to fees, interest, and principal, taking the accrued interest into account.  The next step
	 * will be to "apply" the loanevent, which will potentially trigger a retroactive adjustment 
	 * of all loanevents with a later effective date.
	 * @param loan
	 * @param loanEventType
	 * @param effectiveDate
	 * @return
	 */
	public LoanEvent createLoanEventWithTransaction(Loan loan, LoanEventType loanEventType, Date effectiveDate){
		LoanEvent loanEvent = new LoanEvent();
		loanEvent.setLoanID(loan.getLoanID());
		loanEvent.setEffectiveDate(effectiveDate);
		loanEvent.setLoanEventType(loanEventType);
		loanEvent.setPostDate(systemSettingsLogic.getCurrentSystemDate());
		
		LoanEvent lastEvent = loanEventRepository.findMostRecentLoanEventWithTransactionEffectivePriorToDate(loanEvent.getLoanID(), loanEvent.getEffectiveDate());
		BigDecimal accruedInterest = new BigDecimal(0);
		if(lastEvent != null)
			accruedInterest = interestAccrualLogic.calculateLoanInterestAmountForPeriod(loan, lastEvent.getEffectiveDate(), effectiveDate);
		
		LoanTransaction loanTransaction = new LoanTransaction();
		loanTransaction.setInterestAccrued(accruedInterest);
		loanEvent.setLoanTransaction(loanTransaction);
		return loanEvent;
	}
	
	/**
	 * This method will "apply" the loanevent, which could potentially trigger a retroactive adjustment 
	 * of all loanevents that occured after this loanevent.
	 * @param loanEvent
	 * @return
	 */
	public LoanEvent applyLoanEvent(LoanEvent loanEvent){
		if(loanEvent.getLoanTransaction() != null){
			LoanTransaction savedTransaction = this.loanTransactionRepository.save(loanEvent.getLoanTransaction());
			loanEvent.setLoanTransaction(savedTransaction);
			LoanEvent lastEvent = loanEventRepository.findMostRecentLoanEventWithTransactionEffectivePriorToDate(loanEvent.getLoanID(), loanEvent.getEffectiveDate());
			int lastFees = 0;
			BigDecimal lastInterest = new BigDecimal(0);
			int lastPrincipal = 0;
			if(lastEvent != null){
				LoanTransaction lastTransaction = lastEvent.getLoanTransaction();
				lastFees = lastTransaction.getEndingFees();
				lastInterest = lastTransaction.getEndingInterest();
				lastPrincipal = lastTransaction.getEndingPrincipal();
			}
			savedTransaction.setEndingFees(lastFees - savedTransaction.getFeesChange());
			savedTransaction.setEndingPrincipal(lastPrincipal - savedTransaction.getPrincipalChange());
			savedTransaction.setEndingInterest(lastInterest.subtract(savedTransaction.getInterestChange()));
		}
		return this.loanEventRepository.save(loanEvent);
	}
	
	public LoanEvent findMostRecentLoanEventWithTransactionEffectivePriorToDate(Long loanID, Date theDate){
		return loanEventRepository.findMostRecentLoanEventWithTransactionEffectivePriorToDate(loanID, theDate);
	}
}
