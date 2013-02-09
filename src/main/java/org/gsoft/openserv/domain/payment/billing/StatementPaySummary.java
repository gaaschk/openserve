package org.gsoft.openserv.domain.payment.billing;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.NullComparator;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.util.collections.SortedList;
import org.gsoft.openserv.util.comparator.ComparatorAdapter;
import org.joda.time.DateTime;

public class StatementPaySummary {
	private LoanTypeProfile effectiveLoanTypeProfile;
	private BillingStatement statement;
	private List<StatementPayment> payments;
	
	public BillingStatement getStatement(){
		return statement;
	}
	
	private List<StatementPayment> getPayments(){
		if(this.payments == null){
			ComparatorChain comparator = new ComparatorChain();
			comparator.addComparator(new BeanComparator("payment", new BeanComparator("payment", new BeanComparator("effectiveDate", new NullComparator(true)))));
			comparator.addComparator(new BeanComparator("payment", new BeanComparator("payment", new BeanComparator("postDate", new NullComparator(true)))));
			payments = new SortedList<StatementPayment>(new ComparatorAdapter<StatementPayment>(comparator),true);
		}
		return payments;
	}
	
	public StatementPaySummary(BillingStatement statement, LoanTypeProfile effectiveProfile){
		this.effectiveLoanTypeProfile = effectiveProfile;
		this.statement = statement;
	}
	
	public boolean shouldAssessLateFee(Date effectiveDate){
		Date lateFeeDate = this.expectedLateFeeDate();
		return effectiveDate.after(lateFeeDate) && (this.getSatisfiedDate() == null || this.getSatisfiedDate().after(lateFeeDate));
	}
	
	public Date expectedLateFeeDate(){
		int dpdflf = this.getEffectiveLoanTypeProfile().getDaysLateForFee();
		return new DateTime(this.getStatement().getDueDate()).plusDays(dpdflf).toDate();
	}

	public LoanTypeProfile getEffectiveLoanTypeProfile(){
		return this.effectiveLoanTypeProfile;
	}
	
	public boolean addPayment(StatementPayment payment){
		return this.getPayments().add(payment);
	}
	
	public int getTotalDue(){
		return this.getStatement().getMinimumRequiredPayment();
	}
	
	public int getUnpaidBalance(){
		return this.getTotalDue()-this.getTotalPaid();
	}
	
	public Date getSatisfiedDate(){
		if(this.getPayments().size() > 0 && this.getUnpaidBalance() <= 0){
			int appliedAmount = 0;
			for(StatementPayment payment:this.getPayments()){
				appliedAmount += payment.getAppliedAmount();
				if(appliedAmount >= this.getTotalDue()){
					return payment.getPayment().getPayment().getEffectiveDate();
				}
			}
		}
		return null;
	}

	public int getTotalPaid(){
		int paidAmt = 0;
		for(StatementPayment payment:this.getPayments()){
			paidAmt += payment.getAppliedAmount();
		}
		return paidAmt;
	}
	
	public Date getPrepayDate(){
		int prepayDays = this.getEffectiveLoanTypeProfile().getPrepaymentDays();
		return new DateTime(this.getStatement().getDueDate()).minusDays(prepayDays).toDate();
	}
	
	public void clear(){
		this.getPayments().clear();
	}
}
