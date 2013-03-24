package org.gsoft.openserv.web.rates.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class RateListModel implements Serializable{
	private static final long serialVersionUID = -5896940389339300710L;
	private List<RateModel> rates;
	private Date quoteDate;
	private String newRateSymbol;
	private String newRateName;
	
	public List<RateModel> getRates() {
		return rates;
	}

	public void setRates(List<RateModel> rates) {
		this.rates = rates;
	}

	@DateTimeFormat(pattern="MM/dd/yyyy")
	public Date getQuoteDate() {
		return quoteDate;
	}

	public void setQuoteDate(Date quoteDate) {
		this.quoteDate = quoteDate;
	}

	public String getNewRateSymbol() {
		return newRateSymbol;
	}

	public void setNewRateSymbol(String newRateSymbol) {
		this.newRateSymbol = newRateSymbol;
	}

	public String getNewRateName() {
		return newRateName;
	}

	public void setNewRateName(String newRateName) {
		this.newRateName = newRateName;
	}
}
