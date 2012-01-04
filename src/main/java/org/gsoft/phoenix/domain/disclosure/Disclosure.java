package org.gsoft.phoenix.domain.disclosure;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

@Entity
public class Disclosure implements PhoenixDomainObject {
	private static final long serialVersionUID = -248336175803533637L;

	private Long disclosureID;
	private Long loanID;
	private Date disclosureDate;
	private Date EffectiveDate;
	private List<DisclosurePayment> disclosurePayments;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getDisclosureID() {
		return disclosureID;
	}
	public void setDisclosureID(Long disclosureID) {
		this.disclosureID = disclosureID;
	}
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	public Date getDisclosureDate() {
		return disclosureDate;
	}
	public void setDisclosureDate(Date disclosureDate) {
		this.disclosureDate = disclosureDate;
	}
	public Date getEffectiveDate() {
		return EffectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		EffectiveDate = effectiveDate;
	}
	@OneToMany(mappedBy="disclosure")
	public List<DisclosurePayment> getDisclosurePayments() {
		return disclosurePayments;
	}
	public void setDisclosurePayments(List<DisclosurePayment> disclosurePayments) {
		this.disclosurePayments = disclosurePayments;
	}
}
