package org.gsoft.openserv.domain.payment.billing;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.NullComparator;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.util.collections.SortedList;
import org.gsoft.openserv.util.comparator.ComparatorAdapter;
import org.joda.time.DateTime;

public class LoanStatementSummary {
	private List<LoanPayment> payments;
	private List<StatementPaySummary> statements;
	private List<LoanTypeProfile> loanTypeProfiles;
	
	private List<LoanPayment> getPayments(){
		if(this.payments == null){
			ComparatorChain comparator = new ComparatorChain();
			comparator.addComparator(new BeanComparator("payment", new BeanComparator("effectiveDate", new NullComparator(true))));
			comparator.addComparator(new BeanComparator("payment", new BeanComparator("postDate", new NullComparator(true))));
			payments = new SortedList<LoanPayment>(new ComparatorAdapter<LoanPayment>(comparator),true);
		}
		return payments;
	}
	
	public List<StatementPaySummary> getStatements(){
		if(this.statements == null){
			ComparatorChain comparator = new ComparatorChain();
			comparator.addComparator(new BeanComparator("statement", new BeanComparator("dueDate", new NullComparator(true))));
			comparator.addComparator(new BeanComparator("statement", new BeanComparator("createdDate", new NullComparator(true))));
			statements = new SortedList<StatementPaySummary>(new ComparatorAdapter<StatementPaySummary>(comparator),true);
		}
		return statements;
	}
	
	private List<LoanTypeProfile> getLoanTypeProfiles(){
		if(this.loanTypeProfiles == null){
			this.loanTypeProfiles = new SortedList<>(new ComparatorAdapter<LoanTypeProfile>(new BeanComparator("effectiveDate")),true);
		}
		return this.loanTypeProfiles;
	}
	
	private void clearStatements(){
		for(StatementPaySummary stmt:this.getStatements()){
			stmt.clear();
		}
	}
	
	private List<StatementPaySummary> getEligibleStatements(Date effectiveDate){
		ArrayList<StatementPaySummary> eligStmts = new ArrayList<>();
		for(StatementPaySummary stmt:this.getStatements()){
			LoanTypeProfile ltp = this.getEffectiveLoanTypeProfile(stmt.getStatement().getDueDate());
			int prepayDays = (ltp==null)?0:ltp.getPrepaymentDays();
			Date prepayDate = new DateTime(stmt.getStatement().getDueDate()).minusDays(prepayDays).toDate();
			if(prepayDate.after(effectiveDate))return eligStmts;
			eligStmts.add(stmt);
		}
		return eligStmts;
	}
	

	private LoanTypeProfile getEffectiveLoanTypeProfile(Date effectiveDate){
		LoanTypeProfile ltp = null;
		for(LoanTypeProfile profile:this.getLoanTypeProfiles()){
			if(!profile.getEffectiveDate().before(effectiveDate))
				return ltp;
			ltp = profile;
		}
		return ltp;
	}

	public LoanStatementSummary(){}
	
	public LoanStatementSummary(List<BillingStatement> statements, List<LoanPayment> payments, List<LoanTypeProfile> profiles){
		this.setLoanTypeProfiles(profiles);
		this.setStatements(statements);
		this.setPayments(payments);
	}
	
	public void addPayment(LoanPayment payment){
		this.getPayments().add(payment);
		this.applyPayments();
	}
	
	public void addPayments(List<LoanPayment> payments){
		this.getPayments().addAll(payments);
		this.applyPayments();
	}
	
	public void setPayments(List<LoanPayment> payments){
		this.getPayments().clear();
		this.addPayments(payments);
	}
	
	public void addStatement(BillingStatement statement){
		this.getStatements().add(new StatementPaySummary(statement));
		this.applyPayments();
	}
	
	public void addStatements(List<BillingStatement> statements){
		for(BillingStatement statement:statements){
			this.getStatements().add(new StatementPaySummary(statement));
		}
		this.applyPayments();
	}
	
	public void setStatements(List<BillingStatement> statements){
		this.getStatements().clear();
		this.addStatements(statements);
	}
	
	public void setLoanTypeProfiles(List<LoanTypeProfile> loanTypeProfiles){
		this.getLoanTypeProfiles().clear();
		this.getLoanTypeProfiles().addAll(loanTypeProfiles);
	}
	
	public void applyPayments(){
		this.clearStatements();
		for(LoanPayment payment:this.getPayments()){
			List<StatementPaySummary> eligStmts = this.getEligibleStatements(payment.getPayment().getEffectiveDate());
			Iterator<StatementPaySummary> stmtIter = eligStmts.iterator();
			int remainingPayment = payment.getAppliedAmount()*-1;
			while(stmtIter.hasNext() && remainingPayment > 0){
				StatementPaySummary stmt = stmtIter.next();
				if(!stmtIter.hasNext()){
					stmt.addPayment(new StatementPayment(payment, remainingPayment));
					remainingPayment = 0;
				}
				else{
					int amountToApply = (remainingPayment>=stmt.getUnpaidBalance())?stmt.getUnpaidBalance():remainingPayment;
					stmt.addPayment(new StatementPayment(payment, amountToApply));
					remainingPayment -= amountToApply;
				}
			}
		}
	}
	
	public Date getEarliestUnpaidDueDate(){
		Date dueDate = null;
		for(StatementPaySummary statement:this.getStatements()){
			dueDate = statement.getStatement().getDueDate();
			if(statement.getSatisfiedDate() == null){
				break;
			}
		}
		return dueDate;
	}
	
	public Integer getMinimumPaymentToAdvanceDueDate(){
		Integer amount = null;
		for(StatementPaySummary statement:this.getStatements()){
			if(statement.getSatisfiedDate() == null){
				amount = statement.getStatement().getMinimumRequiredPayment();
				break;
			}
		}
		return amount;
	}
	
	public Date getNextDueDate(){
		if(this.getStatements().size() > 0){
			return this.getStatements().get(this.getStatements().size()-1).getStatement().getDueDate();
		}
		return null;
	}
}
