package org.gsoft.phoenix.web.models;

import java.util.Date;
import java.util.List;

import org.gsoft.phoenix.util.formatter.CurrencyInPenniesFormat;

public class PaymentModel {
	private Long paymentID;
	private Integer paymentAmount;
	private Date paymentEffectiveDate;
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
