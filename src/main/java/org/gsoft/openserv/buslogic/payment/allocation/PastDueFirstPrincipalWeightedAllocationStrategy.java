package org.gsoft.openserv.buslogic.payment.allocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.gsoft.openserv.buslogic.payment.PaymentAllocationLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.domain.payment.billing.LoanStatementSummary;
import org.gsoft.openserv.repositories.loan.LoanStateHistoryRepository;
import org.gsoft.openserv.repositories.payment.LoanStatementRepository;
import org.springframework.stereotype.Component;

/**
 * Bring all past due loans current first.  When all loans are current, or 
 * when the total due of the longest past due loans exceeds the amount of 
 * the available funds, allocate remaining funds to longest past due or all
 * loans, if all current, weighted by principal balance.
 * @author gaaschk
 *
 */
@Component
public class PastDueFirstPrincipalWeightedAllocationStrategy implements
		PaymentAllocationLogic {
	@Resource
	private LoanStateHistoryRepository loanStateHistoryRepo;
	@Resource
	private LoanStatementRepository statementRepo;
	
	private Comparator comparator = null;
	
	private Comparator getLoanAllocationComparator(){
		if(this.comparator == null){
			comparator = new NullComparator(new BeanComparator("projectedPrepayDate", new NullComparator()));
		}
		return comparator;
	}
	
	@Override
	public void allocatePayment(Payment payment, List<Loan> loans) {
		ArrayList<LoanAllocation> allocations = new ArrayList<>();
		ArrayList<LoanPayment> newLoanPayments = new ArrayList<>();
		for(Loan loan:loans){
			LoanStateHistory history = loanStateHistoryRepo.findLoanStateHistoryAsOf(loan, payment.getEffectiveDate());
			LoanStatementSummary statementSummary = statementRepo.getLoanStatementSummaryForLoanAsOfDate(loan, payment.getEffectiveDate());
			LoanPayment loanPayment = new LoanPayment();
			loanPayment.setAppliedAmount(0);
			loanPayment.setLoanID(loan.getLoanID());
			loanPayment.setPayment(payment);
			newLoanPayments.add(loanPayment);
			allocations.add(new LoanAllocation(loan, history, statementSummary, loanPayment));
		}
		int remainingPaymentAmount = payment.getPaymentAmount();
		while(remainingPaymentAmount < 0){
			remainingPaymentAmount = this.allocationAmountToLoans(allocations, remainingPaymentAmount, payment.getEffectiveDate());
		}
		for(LoanPayment loanPayment:newLoanPayments){
			if(loanPayment.getAppliedAmount() < 0){
				loanPayment.setAppliedAmount(loanPayment.getAppliedAmount());
				payment.getLoanPayments().add(loanPayment);
			}
		}
	}
	
	private int allocationAmountToLoans(List<LoanAllocation> allocations, int amountToPay, Date paymentEffectiveDate){
		List<LoanAllocation> oldestAllocations = this.getEarliestDueSubset(allocations, paymentEffectiveDate);
		int totalDue = this.getTotalDueForSet(oldestAllocations);
		int amountRemaining = 0;
		if(totalDue > Math.abs(amountToPay) || !oldestAllocations.get(0).getProjectedPrepayDate().before(paymentEffectiveDate)){
			this.allocateBasedOnDueWeight(oldestAllocations, amountToPay);
		}
		else{
			for(LoanAllocation allocation:oldestAllocations){
				allocation.addToAppliedAmount(allocation.getMinimumPaymentAmount()*-1);
			}
			amountRemaining = amountToPay + totalDue;
		}
		return amountRemaining;
	}
	
	private void allocateBasedOnDueWeight(List<LoanAllocation> allocations, int amountToPay){
		int totalDue = 0;
		for(LoanAllocation allocation:allocations){
			totalDue += allocation.getMinimumPaymentAmount();
		}
		int amountApplied = 0;
		for(LoanAllocation allocation:allocations){
			long loanAmountToApply = ((long)allocation.getMinimumPaymentAmount()*(long)amountToPay)/totalDue;
			allocation.addToAppliedAmount((int)loanAmountToApply);
			amountApplied += loanAmountToApply;
		}
		int index = 0;
		while(amountApplied > amountToPay){
			allocations.get(index).addToAppliedAmount(-1);
			amountApplied--;
			index++;
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<LoanAllocation> getEarliestDueSubset(List<LoanAllocation> allocations, Date paymentEffectiveDate){
		Collections.sort(allocations, this.getLoanAllocationComparator());
		if(allocations.size() <= 1 || !allocations.get(0).getProjectedPrepayDate().before(paymentEffectiveDate))
			return allocations;
		Date oldestDate = allocations.get(0).getProjectedPrepayDate();
		int index = 0;
		ArrayList<LoanAllocation> subset = new ArrayList<>();
		while(index < allocations.size() && !allocations.get(index).getProjectedPrepayDate().after(oldestDate)){
			subset.add(allocations.get(index));
			index++;
		}
		return subset;
	}
	
	private int getTotalDueForSet(List<LoanAllocation> allocations){
		int totalDue = 0;
		for(LoanAllocation allocation:allocations){
			totalDue+=allocation.getMinimumPaymentAmount();
		}
		return totalDue;
	}
}
