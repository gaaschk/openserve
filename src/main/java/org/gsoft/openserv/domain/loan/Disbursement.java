package org.gsoft.openserv.domain.loan;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.OpenServDomainObject;

@Entity
public class Disbursement extends OpenServDomainObject{
	private static final long serialVersionUID = -8488460178826929454L;
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
	@ManyToOne
	@JoinColumn(name="LoanID")
	public Loan getLoan() {
		return loan;
	}
	public void setLoan(Loan theLoan) {
		this.loan = theLoan;
	}
	public Date getDisbursementEffectiveDate() {
		return (disbursementEffectiveDate==null)?null:(Date)disbursementEffectiveDate.clone();
	}
	public void setDisbursementEffectiveDate(Date disbursementEffectiveDate) {
		this.disbursementEffectiveDate = (disbursementEffectiveDate==null)?null:(Date)disbursementEffectiveDate.clone();
	}
	public Integer getDisbursementAmount() {
		return disbursementAmount;
	}
	public void setDisbursementAmount(Integer disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getDisbursementID();
	}
}
