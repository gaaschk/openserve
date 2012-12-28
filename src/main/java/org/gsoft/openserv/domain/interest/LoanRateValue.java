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
public class LoanRateValue extends OpenServDomainObject {
	private static final long serialVersionUID = -6846403423957356884L;

	private Long loanRateValueID;
	private Long loanID;
	private RateValue rateValue;
	private Date lockedDate;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getLoanRateValueID() {
		return loanRateValueID;
	}

	//Used by Hibernate
	@SuppressWarnings("unused")
	private void setLoanRateValueID(Long lockedLoanRateValueID) {
		this.loanRateValueID = lockedLoanRateValueID;
	}

	public Long getLoanID() {
		return loanID;
	}

	public void setLoanID(Long loanID) {
		this.loanID = loanID;
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
 		return this.getLoanRateValueID();
	}

}
