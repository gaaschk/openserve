package org.gsoft.openserv.domain.interest;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.OpenServDomainObject;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class LockedLoanRateValue extends OpenServDomainObject {
	private static final long serialVersionUID = -6846403423957356884L;

	private Long lockedLoanRateValueID;
	private Long loanId;
	private RateValue rateValue;
	private Date lockedDate;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getLockedLoanRateValueID() {
		return lockedLoanRateValueID;
	}

	//Used by Hibernate
	@SuppressWarnings("unused")
	private void setLockedLoanRateValueID(Long lockedLoanRateValueID) {
		this.lockedLoanRateValueID = lockedLoanRateValueID;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	@ManyToOne
	@JoinColumn(name="RateValueID", insertable=false, updatable=false)
	public RateValue getRateValue() {
		return rateValue;
	}

	public void setRateValue(RateValue rateValue) {
		this.rateValue = rateValue;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	@Override
	@Transient
	public Long getID() {
 		return this.getLockedLoanRateValueID();
	}

}
