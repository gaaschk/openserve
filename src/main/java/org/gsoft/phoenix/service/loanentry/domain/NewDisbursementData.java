package org.gsoft.phoenix.service.loanentry.domain;

import java.util.Date;

public class NewDisbursementData {
	private Date disbursementDate;
	private Integer disbursementAmount;
	
	public Date getDisbursementDate() {
		return disbursementDate;
	}
	public void setDisbursementDate(Date disbursementDate) {
		this.disbursementDate = disbursementDate;
	}
	public Integer getDisbursementAmount() {
		return disbursementAmount;
	}
	public void setDisbursementAmount(Integer disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}
}
