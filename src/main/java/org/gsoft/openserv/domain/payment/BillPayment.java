package org.gsoft.openserv.domain.payment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.gsoft.openserv.domain.OpenServDomainObject;

@Entity
public class BillPayment extends OpenServDomainObject{
	private static final long serialVersionUID = 5401973510782252658L;
	private Long billPaymentID;
	private Integer amountAppliedToBill;
	//Relationships
	private BillingStatement billingStatement;
	private LoanPayment loanPayment;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getBillPaymentID() {
		return billPaymentID;
	}
	public void setBillPaymentID(Long billPaymentID) {
		this.billPaymentID = billPaymentID;
	}
	public Integer getAmountAppliedToBill() {
		return amountAppliedToBill;
	}
	public void setAmountAppliedToBill(Integer amountAppliedToBill) {
		this.amountAppliedToBill = amountAppliedToBill;
	}
	@ManyToOne
	@JoinColumn(name="BillingStatementID")
	public BillingStatement getBillingStatement() {
		return billingStatement;
	}
	public void setBillingStatement(BillingStatement billingStatement) {
		this.billingStatement = billingStatement;
	}
	@ManyToOne
	@JoinColumn(name="LoanPaymentID")
	public LoanPayment getLoanPayment() {
		return loanPayment;
	}
	public void setLoanPayment(LoanPayment loanPayment) {
		this.loanPayment = loanPayment;
	}
	
	@Override
	public boolean equals(Object arg1){
		if(arg1 != null && arg1 instanceof BillPayment){
			BillPayment other = (BillPayment)arg1;
			if(this.getBillingStatement() == null)return other.getBillingStatement() == null;
			if(this.getLoanPayment() == null)return other.getLoanPayment() == null;
			return (this.getBillingStatement().getBillingStatementID().equals(other.getBillingStatement().getBillingStatementID())) && 
				(this.getLoanPayment().getLoanPaymentID().equals(other.getLoanPayment().getLoanPaymentID()));
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(this.getBillingStatement().getBillingStatementID()).append(this.getLoanPayment().getLoanPaymentID()).toHashCode();
	}
	
	@Override
	@Transient
	public Long getID(){
		return this.getBillPaymentID();
	}
}
