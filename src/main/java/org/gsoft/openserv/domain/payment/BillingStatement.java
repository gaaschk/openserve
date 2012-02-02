package org.gsoft.openserv.domain.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PhoenixDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class BillingStatement extends PhoenixDomainObject{
	private static final long serialVersionUID = -7042442632267944606L;
	private Long billingStatementID;
	private Long loanID;
	private Date createdDate;
	private Date dueDate;
	private Integer minimumRequiredPayment = 0;
	private Integer paidAmount = 0;
	private Date satisfiedDate;
	//Relationships
	private List<BillPayment> billPayments;
	private LateFee lateFee;

	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getBillingStatementID() {
		return billingStatementID;
	}
	public void setBillingStatementID(Long billingStatementID) {
		this.billingStatementID = billingStatementID;
	}
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Integer getMinimumRequiredPayment() {
		if(minimumRequiredPayment == null)
			minimumRequiredPayment = 0;
		return minimumRequiredPayment;
	}
	public void setMinimumRequiredPayment(Integer minimumRequiredPayment) {
		this.minimumRequiredPayment = minimumRequiredPayment;
	}
	@OneToMany(cascade=CascadeType.ALL, mappedBy="billingStatement")
	public List<BillPayment> getBillPayments() {
		if(billPayments == null){
			billPayments = new ArrayList<BillPayment>();
		}
		return billPayments;
	}
	public void setBillPayments(List<BillPayment> billPayments) {
		this.billPayments = billPayments;
	}
	public Integer getPaidAmount(){
		if(paidAmount == null)
			paidAmount = 0;
		return paidAmount;
	}
	public void setPaidAmount(Integer paidAmount){
		this.paidAmount = paidAmount;
	}
	public Date getSatisfiedDate(){
		return this.satisfiedDate;
	}
	public void setSatisfiedDate(Date satisfiedDate){
		this.satisfiedDate = satisfiedDate;
	}
	@OneToOne(mappedBy="billingStatement")
	public LateFee getLateFee(){
		return this.lateFee;
	}
	public void setLateFee(LateFee lateFee){
		this.lateFee = lateFee;
	}
	@Transient
	public Integer getUnpaidBalance(){
		int unpaidAmount = this.getMinimumRequiredPayment() - this.getPaidAmount(); 
		return (unpaidAmount<0)?0:unpaidAmount;
	}
	@Override
	@Transient
	public Long getID(){
		return this.getLoanID();
	}
	
	public boolean removePayment(BillPayment payment){
		boolean success = this.getBillPayments().remove(payment);
		if(success){
			this.setPaidAmount(this.getPaidAmount() - payment.getAmountAppliedToBill());
			if(this.getPaidAmount()<this.getMinimumRequiredPayment())
				this.setSatisfiedDate(null);
		}
		return success;
	}
	
	public BillPayment addPayment(LoanPayment payment, int amount){
		BillPayment newPayment = new BillPayment();
		this.getBillPayments().add(newPayment);
		newPayment.setBillingStatement(this);
		newPayment.setLoanPayment(payment);
		newPayment.setAmountAppliedToBill(amount);
		this.setPaidAmount(this.getPaidAmount()+amount);
		if(this.getSatisfiedDate() == null && this.getUnpaidBalance()<=0)
			this.setSatisfiedDate(payment.getPayment().getEffectiveDate());
		return newPayment;
	}
}
