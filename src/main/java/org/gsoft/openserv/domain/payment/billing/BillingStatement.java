package org.gsoft.openserv.domain.payment.billing;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class BillingStatement extends PersistentDomainObject{
	private static final long serialVersionUID = -7042442632267944606L;
	private Long billingStatementID;
	private Long loanID;
	private Date createdDate;
	private Date dueDate;
	private Integer minimumRequiredPayment = 0;

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

	@Override
	@Transient
	public Long getID(){
		return this.getLoanID();
	}
}
