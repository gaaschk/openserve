package org.gsoft.phoenix.domain.loan;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Disbursement {
	private Long disbursementID;
	private Loan loan;
	private Date disbursementEffectiveDate;
	private Integer disbursementAmount;

	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getDisbursementID() {
		return disbursementID;
	}
	public void setDisbursementID(Long disbursementID) {
		this.disbursementID = disbursementID;
	}
	public Loan getLoan() {
		return loan;
	}
	@ManyToOne
	@JoinColumn(name="LoanID", insertable=false, updatable=false)
	public void setLoan(Loan theLoan) {
		this.loan = theLoan;
	}
	public Date getDisbursementEffectiveDate() {
		return disbursementEffectiveDate;
	}
	public void setDisbursementEffectiveDate(Date disbursementEffectiveDate) {
		this.disbursementEffectiveDate = disbursementEffectiveDate;
	}
	public Integer getDisbursementAmount() {
		return disbursementAmount;
	}
	public void setDisbursementAmount(Integer disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}
}
