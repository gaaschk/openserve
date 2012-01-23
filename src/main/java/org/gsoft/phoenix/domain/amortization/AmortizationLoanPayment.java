package org.gsoft.phoenix.domain.amortization;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

@Entity
public class AmortizationLoanPayment extends PhoenixDomainObject{
	private static final long serialVersionUID = 3438237766896319509L;
	private Long amortizationLoanPaymentID;
	private Integer paymentAmount;
	private Integer paymentCount;
	private LoanAmortizationSchedule loanAmortizationSchedule;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getAmortizationLoanPaymentID() {
		return amortizationLoanPaymentID;
	}
	public void setAmortizationLoanPaymentID(Long amortizationLoanPaymentID) {
		this.amortizationLoanPaymentID = amortizationLoanPaymentID;
	}
	public Integer getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Integer getPaymentCount() {
		return paymentCount;
	}
	public void setPaymentCount(Integer paymentCount) {
		this.paymentCount = paymentCount;
	}
	@ManyToOne
	@JoinColumn(name="LoanAmortizationScheduleID")
	public LoanAmortizationSchedule getLoanAmortizationSchedule() {
		return loanAmortizationSchedule;
	}
	public void setLoanAmortizationSchedule(LoanAmortizationSchedule loanAmortizationSchedule) {
		this.loanAmortizationSchedule = loanAmortizationSchedule;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getAmortizationLoanPaymentID();
	}
}
