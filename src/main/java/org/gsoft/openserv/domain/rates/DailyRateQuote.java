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

import org.gsoft.openserv.domain.OpenServDomainObject;

@Entity
public class DailyRateQuote extends OpenServDomainObject{
	private static final long serialVersionUID = 5200896181147108602L;
	private Long dailyRateQuoteID;
	private Date quoteDate;
	private BigDecimal value;
	//Relationships
	private Rate rate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getDailyRateQuoteID() {
		return dailyRateQuoteID;
	}
	public void setDailyRateQuoteID(Long dailyRateQuoteID) {
		this.dailyRateQuoteID = dailyRateQuoteID;
	}
	public Date getQuoteDate() {
		return quoteDate;
	}
	public void setQuoteDate(Date quoteDate) {
		this.quoteDate = quoteDate;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	@ManyToOne
	@JoinColumn(name="rateID")
	public Rate getRate() {
		return rate;
	}
	public void setRate(Rate rate) {
		this.rate = rate;
	}
	@Transient
	@Override
	public Long getID() {
		return this.getDailyRateQuoteID();
	}
}
