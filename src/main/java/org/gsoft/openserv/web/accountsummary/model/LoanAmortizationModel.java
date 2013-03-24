package org.gsoft.openserv.web.accountsummary.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class LoanAmortizationModel implements Serializable{
	private static final long serialVersionUID = -1765502286384185279L;

	private Date creationDate;
	private Date effectiveDate;
	private List<AmortizationPaymentGroupModel> paymentGroups;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public List<AmortizationPaymentGroupModel> getPaymentGroups() {
		return paymentGroups;
	}
	public void setPaymentGroups(List<AmortizationPaymentGroupModel> paymentGroups) {
		this.paymentGroups = paymentGroups;
	}
}
