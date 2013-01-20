package org.gsoft.openserv.domain.rates;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class RateValue extends PersistentDomainObject {
	private static final long serialVersionUID = 5983796240499342749L;
	private Long rateValueId;
	private Rate rate;
	private BigDecimal rateValue;
	private Date rateValueDate;
	private Boolean isValid;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getRateValueId() {
		return rateValueId;
	}

	//Needed for Hibernate
	@SuppressWarnings("unused")
	private void setRateValueId(Long rateValueId) {
		this.rateValueId = rateValueId;
	}

	@ManyToOne
	@JoinColumn(name="RateID", insertable=false, updatable=false)
	public Rate getRate() {
		return rate;
	}

	public void setRate(Rate rate) {
		this.rate = rate;
	}

	public BigDecimal getRateValue() {
		return rateValue;
	}

	public void setRateValue(BigDecimal rateValue) {
		this.rateValue = rateValue;
	}

	public Date getRateValueDate() {
		return rateValueDate;
	}

	public void setRateValueDate(Date rateValueDate) {
		this.rateValueDate = rateValueDate;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean valid) {
		this.isValid = valid;
	}

	@Override
	@Transient
	public Long getID() {
		return this.getRateValueId();
	}

}
