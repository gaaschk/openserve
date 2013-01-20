package org.gsoft.openserv.buslogic.payment.allocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.payment.PaymentAllocationLogic;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanStateHistory;
import org.gsoft.openserv.domain.payment.BillingStatement;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.Payment;
import org.gsoft.openserv.repositories.loan.LoanStateHistoryRepository;
import org.gsoft.openserv.repositories.payment.BillingStatementRepository;
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
	private BillingStatementRepository billingStatementRepo;
	
	private Comparator<LoanAllocation> comparator = null;
	
	private Comparator<LoanAllocation> getLoanAllocationComparator(){
		if(this.comparator == null){
			comparator = new Comparator<LoanAllocation>() {
				@Override
				public int compare(LoanAllocation o1, LoanAllocation o2) {
					if(o1 == o2)
						return 0;
					if(o1 == null)
						return -1;
					if(o2 == null)
						return 1;
					if(o1.getProjectedDueDate() == o2.getProjectedDueDate())
						return 0;
					if(o1.getProjectedDueDate() == null)
						return -1;
					if(o2.getProjectedDueDate() == null)
						return 1;
					return o1.getProjectedDueDate().compareTo(o2.getProjectedDueDate());
				}
			};
		}
		return comparator;
	}
	
	@Override
	public void allocatePayment(Payment payment, List<Loan> loans) {
		ArrayList<LoanAllocation> allocations = new ArrayList<>();
		for(Loan loan:loans){
			LoanStateHistory history = loanStateHistoryRepo.findLoanStateHistoryAsOf(loan, payment.getEffectiveDate());
			List<BillingStatement> statements = billingStatementRepo.findAllBillsForLoanOnOrBefore(loan.getLoanID(), payment.getEffectiveDate());
			allocations.add(new LoanAllocation(loan, history, statements));
		}
		int remainingPaymentAmount = payment.getPaymentAmount();
		while(remainingPaymentAmount > 0){
			remainingPaymentAmount = this.allocationAmountToLoans(allocations, remainingPaymentAmount, payment.getEffectiveDate());
		}
		for(LoanAllocation allocation:allocations){
			LoanPayment loanPayment = new LoanPayment();
			loanPayment.setAppliedAmount(allocation.getAppliedAmount()*-1);
			loanPayment.setLoanID(allocation.getLoan().getLoanID());
			loanPayment.setPayment(payment);
			payment.getLoanPayments().add(loanPayment);
		}
	}
	
	private int allocationAmountToLoans(List<LoanAllocation> allocations, int amountToPay, Date paymentEffectiveDate){
		List<LoanAllocation> oldestAllocations = this.getEarliestDueSubset(allocations, paymentEffectiveDate);
		int totalDue = this.getTotalDueForSet(oldestAllocations);
		int amountRemaining = 0;
		if(totalDue > amountToPay || !oldestAllocations.get(0).getProjectedDueDate().before(paymentEffectiveDate)){
			this.allocateBasedOnPrincipalWeight(oldestAllocations, amountToPay);
		}
		else{
			for(LoanAllocation allocation:oldestAllocations){
				allocation.addToAppliedAmount(allocation.getMinimumPaymentAmount());
			}
			amountRemaining = amountToPay - totalDue;
		}
		return amountRemaining;
	}
	
	private void allocateBasedOnPrincipalWeight(List<LoanAllocation> allocations, int amountToPay){
		int totalPrincipal = 0;
		for(LoanAllocation allocation:allocations){
			totalPrincipal += allocation.getLoanStateHistory().getEndingPrincipal();
		}
		int amountApplied = 0;
		for(LoanAllocation allocation:allocations){
			long loanAmountToApply = ((long)allocation.getLoanStateHistory().getEndingPrincipal()*(long)amountToPay)/totalPrincipal;
			allocation.addToAppliedAmount((int)loanAmountToApply);
			amountApplied += loanAmountToApply;
		}
		int index = 0;
		while(amountApplied < amountToPay){
			allocations.get(index).addToAppliedAmount(1);
			amountApplied++;
			index++;
		}
	}
	
	private List<LoanAllocation> getEarliestDueSubset(List<LoanAllocation> allocations, Date paymentEffectiveDate){
		Collections.sort(allocations, this.getLoanAllocationComparator());
		if(allocations.size() <= 1 || !allocations.get(0).getProjectedDueDate().before(paymentEffectiveDate))
			return allocations;
		Date oldestDate = allocations.get(0).getProjectedDueDate();
		int index = 0;
		ArrayList<LoanAllocation> subset = new ArrayList<>();
		while(!allocations.get(index).getProjectedDueDate().after(oldestDate)){
			subset.add(allocations.get(index));
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
