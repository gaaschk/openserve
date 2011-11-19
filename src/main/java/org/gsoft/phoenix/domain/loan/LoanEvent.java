package org.gsoft.phoenix.domain.loan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.gsoft.phoenix.domain.PhoenixDomainObject;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
public class LoanEvent implements PhoenixDomainObject{
	private static final long serialVersionUID = 3330293203610183990L;
	private Long loanEventID;
	private Long loanID;
	private LoanEventType loanEventType;
	private Date effectiveDate;
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
}
