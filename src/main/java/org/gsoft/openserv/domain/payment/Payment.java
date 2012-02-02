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
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PhoenixDomainObject;

@Entity
public class Payment extends PhoenixDomainObject{
	private static final long serialVersionUID = -7219483954230532294L;
	private Long paymentID;
	private Long borrowerPersonID;
	private Integer paymentAmount;
	private Date postDate;
	private Date effectiveDate;
	private List<LoanPayment> loanPayments;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(Long paymentID) {
		this.paymentID = paymentID;
	}
	public Long getBorrowerPersonID() {
		return borrowerPersonID;
	}
	public void setBorrowerPersonID(Long borrowerPersonID) {
		this.borrowerPersonID = borrowerPersonID;
	}
	public Integer getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	@OneToMany(cascade=CascadeType.ALL, mappedBy="payment")
	public List<LoanPayment> getLoanPayments() {
		if(loanPayments == null)
			loanPayments = new ArrayList<LoanPayment>();
		return loanPayments;
	}
	public void setLoanPayments(List<LoanPayment> loanPayments) {
		this.loanPayments = loanPayments;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getPaymentID();
	}
}
