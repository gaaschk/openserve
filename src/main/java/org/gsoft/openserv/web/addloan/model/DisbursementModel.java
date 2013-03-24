package org.gsoft.openserv.web.addloan.model;

import java.io.Serializable;
import java.util.Date;

import org.gsoft.openserv.web.support.formatter.currency.CurrencyInPenniesFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class DisbursementModel implements Serializable{
	private static final long serialVersionUID = 8098319578885326678L;
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date disbursementDate = new Date();
	private Integer disbursementAmount;
	
	public Date getDisbursementDate() {
		return disbursementDate;
	}
	public void setDisbursementDate(Date disbursementDate) {
		this.disbursementDate = disbursementDate;
	}
	@CurrencyInPenniesFormat
	public Integer getDisbursementAmount() {
		return disbursementAmount;
	}
	public void setDisbursementAmount(Integer disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}
}
