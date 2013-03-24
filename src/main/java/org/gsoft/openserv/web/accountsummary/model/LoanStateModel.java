package org.gsoft.openserv.web.accountsummary.model;

import java.math.BigDecimal;
import java.util.Date;

import org.gsoft.openserv.web.support.formatter.currency.CurrencyInPenniesFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class LoanStateModel {
	private String eventType;
	private Integer startingPrincipal;
	private BigDecimal startingInterest;
	private Integer startingFees;
	private Integer endingPrincipal;
	private BigDecimal endingInterest;
	private Integer endingFees;
	private BigDecimal accruedInterest;
	private Date effectiveDate;
	private Date postedDate;
	
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	@CurrencyInPenniesFormat
	public Integer getStartingPrincipal() {
		return startingPrincipal;
	}
	public void setStartingPrincipal(Integer startingPrincipal) {
		this.startingPrincipal = startingPrincipal;
	}
	@CurrencyInPenniesFormat(subPennyPrecision=6)
	public BigDecimal getStartingInterest() {
		return startingInterest;
	}
	public void setStartingInterest(BigDecimal startingInterest) {
		this.startingInterest = startingInterest;
	}
	@CurrencyInPenniesFormat
	public Integer getStartingFees() {
		return startingFees;
	}
	public void setStartingFees(Integer startingFees) {
		this.startingFees = startingFees;
	}
	@CurrencyInPenniesFormat
	public Integer getEndingPrincipal() {
		return endingPrincipal;
	}
	public void setEndingPrincipal(Integer endingPrincipal) {
		this.endingPrincipal = endingPrincipal;
	}
	@CurrencyInPenniesFormat(subPennyPrecision=6)
	public BigDecimal getEndingInterest() {
		return endingInterest;
	}
	public void setEndingInterest(BigDecimal endingInterest) {
		this.endingInterest = endingInterest;
	}
	@CurrencyInPenniesFormat
	public Integer getEndingFees() {
		return endingFees;
	}
	public void setEndingFees(Integer endingFees) {
		this.endingFees = endingFees;
	}
	@CurrencyInPenniesFormat
	public BigDecimal getAccruedInterest() {
		return accruedInterest;
	}
	public void setAccruedInterest(BigDecimal accruedInterest) {
		this.accruedInterest = accruedInterest;
	}
	@CurrencyInPenniesFormat
	public Integer getPrincipalChange(){
		return this.getEndingPrincipal() - this.getStartingPrincipal();
	}
	public void setPrincipalChange(Integer change){}
	@CurrencyInPenniesFormat
	public BigDecimal getInterestChange(){
		return this.getEndingInterest().subtract(this.getStartingInterest());
	}
	public void setInterestChange(BigDecimal change){}
	@CurrencyInPenniesFormat
	public Integer getFeesChange(){
		return this.getEndingFees() - this.getStartingFees();
	}
	public void setFeesChange(Integer change){}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
}
