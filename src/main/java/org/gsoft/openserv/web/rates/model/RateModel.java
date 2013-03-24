package org.gsoft.openserv.web.rates.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gsoft.openserv.web.support.formatter.percent.PercentFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class RateModel implements Serializable {
	private static final long serialVersionUID = -4259966700562331837L;
	private Long rateID;
	private String symbol;
	private String name;
	private Date quoteDate;
	private BigDecimal value;
	
	public Long getRateID() {
		return rateID;
	}
	public void setRateID(Long rateID) {
		this.rateID = rateID;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@DateTimeFormat(pattern="MM/dd/yyyy")
	public Date getQuoteDate() {
		return quoteDate;
	}
	public void setQuoteDate(Date quoteDate) {
		this.quoteDate = quoteDate;
	}
	@PercentFormat
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	/**
	 * this is going to have to be a temporary solution until I can figure out
	 * how to get the percent formatter working correctly
	 * @return
	 */
	public String getStrValue() {
		if(value == null)
			return "0.0%";
		return value.multiply(new BigDecimal(100)).toString()+"%";
	}
	public void setStrValue(String strValue) {
		strValue = strValue.replaceAll("%", "");
		this.value = new BigDecimal(strValue).divide(new BigDecimal(100));
	}
}
