package org.gsoft.phoenix.web.controller.addloan.model;

import java.io.Serializable;
import java.util.Date;

import org.gsoft.phoenix.util.formatter.CurrencyInPenniesFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class DisbursementModel implements Serializable{
	private static final long serialVersionUID = 8098319578885326678L;
	private Date disbursementDate = new Date();
	private Integer disbursementAmount;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
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
