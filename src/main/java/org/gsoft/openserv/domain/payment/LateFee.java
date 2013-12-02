package org.gsoft.openserv.domain.payment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class LateFee extends PersistentDomainObject{
	private static final long serialVersionUID = 8734376381093496118L;
	private Long lateFeeID;
	private Integer feeAmount;
	private Long billingStatementID;
	private Boolean isCancelled;
	private Date effectiveDate;
	private Date postedDate;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getLateFeeID() {
		return lateFeeID;
	}
	public void setLateFeeID(Long lateFeeID) {
		this.lateFeeID = lateFeeID;
	}
	public Integer getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(Integer feeAmount) {
		this.feeAmount = feeAmount;
	}
	public Long getBillingStatementID() {
		return billingStatementID;
	}
	public void setBillingStatementID(Long billingStatementID) {
		this.billingStatementID = billingStatementID;
	}
	@Column(columnDefinition = "SMALLINT")
	public Boolean isCancelled() {
		return isCancelled;
	}
	public void setCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getLateFeeID();
	}
}
