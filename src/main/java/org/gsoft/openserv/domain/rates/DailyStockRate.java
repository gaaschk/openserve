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
public class DailyStockRate extends OpenServDomainObject{
	private static final long serialVersionUID = 5200896181147108602L;
	private Long dailyStockRateID;
	private Date quoteDate;
	private BigDecimal openValue;
	private BigDecimal lastValue;
	private BigDecimal lowValue;
	private BigDecimal highValue;
	//Relationships
	private Stock stock;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getDailyStockRateID() {
		return dailyStockRateID;
	}
	public void setDailyStockRateID(Long dailyStockRateID) {
		this.dailyStockRateID = dailyStockRateID;
	}
	public Date getQuoteDate() {
		return quoteDate;
	}
	public void setQuoteDate(Date quoteDate) {
		this.quoteDate = quoteDate;
	}
	public BigDecimal getOpenValue() {
		return openValue;
	}
	public void setOpenValue(BigDecimal openValue) {
		this.openValue = openValue;
	}
	public BigDecimal getLastValue() {
		return lastValue;
	}
	public void setLastValue(BigDecimal lastValue) {
		this.lastValue = lastValue;
	}
	public BigDecimal getLowValue() {
		return lowValue;
	}
	public void setLowValue(BigDecimal lowValue) {
		this.lowValue = lowValue;
	}
	public BigDecimal getHighValue() {
		return highValue;
	}
	public void setHighValue(BigDecimal highValue) {
		this.highValue = highValue;
	}
	@ManyToOne
	@JoinColumn(name="stockID")
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	@Transient
	@Override
	public Long getID() {
		// TODO Auto-generated method stub
		return null;
	}
}
