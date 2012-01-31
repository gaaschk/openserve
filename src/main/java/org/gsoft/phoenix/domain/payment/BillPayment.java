package org.gsoft.phoenix.domain.payment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

@Entity
public class BillPayment extends PhoenixDomainObject{
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
		if(arg1 != null && arg1 instanceof BillPayment && ((BillPayment)arg1).getLoanPayment() == null){
			BillPayment other = (BillPayment)arg1;
			if(this.getLoanPayment().getPayment().getEffectiveDate().equals(other.getLoanPayment().getPayment().getEffectiveDate())){
				return this.getLoanPayment().getLoanPaymentID().equals(this.getLoanPayment().getLoanPaymentID());
			}
			return this.getLoanPayment().getPayment().getEffectiveDate().equals(other.getLoanPayment().getPayment().getEffectiveDate());
		}
		return false;
	}
	
	@Override
	@Transient
	public Long getID(){
		return this.getBillPaymentID();
	}
}
