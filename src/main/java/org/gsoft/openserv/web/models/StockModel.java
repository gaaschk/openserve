package org.gsoft.openserv.web.models;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

public class StockModel {
	private Long stockID;
	private String symbol;
	private String name;
	private Boolean autoUpdate;
	private Date quoteDate;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal last;
	
	public Long getStockID() {
		return stockID;
	}
	public void setStockID(Long stockID) {
		this.stockID = stockID;
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
	public Boolean getAutoUpdate() {
		return autoUpdate;
	}
	public void setAutoUpdate(Boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}
	@DateTimeFormat(pattern="MM/dd/yyyy")
	public Date getQuoteDate() {
		return quoteDate;
	}
	public void setQuoteDate(Date quoteDate) {
		this.quoteDate = quoteDate;
	}
	@NumberFormat(pattern="$#,###.00")
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	@NumberFormat(pattern="$#,###.00")
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	@NumberFormat(pattern="$#,###.00")
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	@NumberFormat(pattern="$#,###.00")
	public BigDecimal getLast() {
		return last;
	}
	public void setLast(BigDecimal last) {
		this.last = last;
	}
}
