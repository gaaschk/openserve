package org.gsoft.openserv.domain.loan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.OpenServDomainObject;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
public class LoanEvent extends OpenServDomainObject{
	private static final long serialVersionUID = 3330293203610183990L;
	private Long loanEventID;
	private Long loanID;
	private LoanEventType loanEventType;
	private Date effectiveDate;
	private Integer sequence;
	private Date postDate;
	
	private LoanTransaction loanTransaction;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getLoanEventID() {
		return loanEventID;
	}


	public void setLoanEventID(Long loanEventID) {
		this.loanEventID = loanEventID;
	}


	public Long getLoanID() {
		return loanID;
	}


	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}


	@Type( type = "org.gsoft.phoenix.util.jpa.GenericEnumUserType", parameters={
			@Parameter(name = "enumClass", value = "org.gsoft.phoenix.domain.loan.LoanEventType")
	})
    @Column( name = "LoanEventTypeID" )
	public LoanEventType getLoanEventType() {
		return loanEventType;
	}


	public void setLoanEventType(LoanEventType loanEventType) {
		this.loanEventType = loanEventType;
	}


	public Date getEffectiveDate() {
		return effectiveDate;
	}


	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}


	public Integer getSequence() {
		return sequence;
	}


	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}


	public Date getPostDate() {
		return postDate;
	}


	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	@OneToOne
	@JoinColumn(name="LoanTransactionID")
	public LoanTransaction getLoanTransaction() {
		return loanTransaction;
	}


	public void setLoanTransaction(LoanTransaction loanTransaction) {
		this.loanTransaction = loanTransaction;
	}

	@Override
	@Transient
	public Long getID() {
		return this.getLoanEventID();
	}
}
