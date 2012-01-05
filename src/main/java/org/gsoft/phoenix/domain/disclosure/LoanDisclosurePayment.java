package org.gsoft.phoenix.domain.disclosure;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

@Entity
public class LoanDisclosurePayment implements PhoenixDomainObject{
	private static final long serialVersionUID = 3438237766896319509L;
	private Long loanDisclosurePaymentID;
	private Integer paymentAmount;
	private Integer paymentCount;
	private LoanDisclosure loanDisclosure;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getLoanDisclosurePaymentID() {
		return loanDisclosurePaymentID;
	}
	public void setLoanDisclosurePaymentID(Long loanDisclosurePaymentID) {
		this.loanDisclosurePaymentID = loanDisclosurePaymentID;
	}
	public Integer getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Integer getPaymentCount() {
		return paymentCount;
	}
	public void setPaymentCount(Integer paymentCount) {
		this.paymentCount = paymentCount;
	}
	@ManyToOne
	@JoinColumn(name="DisclosureID", insertable=false, updatable=false)
	public LoanDisclosure getLoanDisclosure() {
		return loanDisclosure;
	}
	public void setLoanDisclosure(LoanDisclosure loanDisclosure) {
		this.loanDisclosure = loanDisclosure;
	}
}
