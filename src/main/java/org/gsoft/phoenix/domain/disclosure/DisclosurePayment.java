package org.gsoft.phoenix.domain.disclosure;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

@Entity
public class DisclosurePayment implements PhoenixDomainObject{
	private static final long serialVersionUID = 3438237766896319509L;
	private Long disclosurePaymentID;
	private Integer paymentAmount;
	private Integer paymentCount;
	private Disclosure disclosure;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getDisclosurePaymentID() {
		return disclosurePaymentID;
	}
	public void setDisclosurePaymentID(Long disclosurePaymentID) {
		this.disclosurePaymentID = disclosurePaymentID;
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
	public Disclosure getDisclosure() {
		return disclosure;
	}
	public void setDisclosure(Disclosure disclosure) {
		this.disclosure = disclosure;
	}
}
