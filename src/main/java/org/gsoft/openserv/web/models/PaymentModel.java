package org.gsoft.openserv.web.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gsoft.openserv.web.formatter.currency.CurrencyInPenniesFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class PaymentModel implements Serializable{
	private static final long serialVersionUID = -3946500836466970L;
	private Long paymentID;
	private Integer paymentAmount;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date paymentEffectiveDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date paymentPostDate;
	private List<LoanPaymentModel> loanPayments;
	
	public Long getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(Long paymentID) {
		this.paymentID = paymentID;
	}
	@CurrencyInPenniesFormat
	public Integer getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Date getPaymentEffectiveDate() {
		return paymentEffectiveDate;
	}
	public void setPaymentEffectiveDate(Date paymentEffectiveDate) {
		this.paymentEffectiveDate = paymentEffectiveDate;
	}
	public Date getPaymentPostDate() {
		return paymentPostDate;
	}
	public void setPaymentPostDate(Date paymentPostDate) {
		this.paymentPostDate = paymentPostDate;
	}
	public List<LoanPaymentModel> getLoanPayments() {
		return loanPayments;
	}
	public void setLoanPayments(List<LoanPaymentModel> loanPayments) {
		this.loanPayments = loanPayments;
	}
}
