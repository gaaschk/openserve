package org.gsoft.openserv.web.addloan.model;

import java.io.Serializable;
import java.util.Date;

import org.gsoft.openserv.lang.Money;
import org.gsoft.openserv.util.support.converter.JsonMoneyDeserializer;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DisbursementModel implements Serializable{
	private static final long serialVersionUID = 8098319578885326678L;
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date disbursementDate = new Date();
	private Money disbursementAmount;
	
	public Date getDisbursementDate() {
		return disbursementDate;
	}
	public void setDisbursementDate(Date disbursementDate) {
		this.disbursementDate = disbursementDate;
	}
	@JsonDeserialize(using=JsonMoneyDeserializer.class)
	public Money getDisbursementAmount() {
		return disbursementAmount;
	}
	public void setDisbursementAmount(Money disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}
}
