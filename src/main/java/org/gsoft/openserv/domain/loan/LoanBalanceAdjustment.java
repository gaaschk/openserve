package org.gsoft.openserv.domain.loan;

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
public class LoanBalanceAdjustment extends PersistentDomainObject{
	private static final long serialVersionUID = 1401102307953286090L;

	private Long loanBalanceAdjustmentID;
	private Long loanID;
	private Integer principalChange;
	private Integer interestChange;
	private Integer feesChange;
	private Date effectiveDate;
	private Date postDate;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getLoanBalanceAdjustmentID() {
		return loanBalanceAdjustmentID;
	}
	
	//Used by hibernate
	@SuppressWarnings("unused")
	private void setLoanBalanceAdjustmentID(Long loanBalanceAdjustmentID) {
		this.loanBalanceAdjustmentID = loanBalanceAdjustmentID;
	}
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public Integer getPrincipalChange() {
		return principalChange;
	}
	public void setPrincipalChange(Integer principalChange) {
		this.principalChange = principalChange;
	}
	public Integer getInterestChange() {
		return interestChange;
	}
	public void setInterestChange(Integer interestChange) {
		this.interestChange = interestChange;
	}
	public Integer getFeesChange() {
		return feesChange;
	}
	public void setFeesChange(Integer feesChange) {
		this.feesChange = feesChange;
	}

	@Override
	@Transient
	public Long getID(){
		return this.getLoanBalanceAdjustmentID();
	}
}
