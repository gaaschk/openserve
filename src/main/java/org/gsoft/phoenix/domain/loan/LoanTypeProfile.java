package org.gsoft.phoenix.domain.loan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
public class LoanTypeProfile {
	private Long loanTypeProfileID;
	private LoanType loanType;
	private Date effectiveDate;
	private Date endDate;
	private Integer maximumLoanTerm;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getLoanTypeProfileID() {
		return loanTypeProfileID;
	}
	public void setLoanTypeProfileID(Long loanTypeProfileID) {
		this.loanTypeProfileID = loanTypeProfileID;
	}
	@Type( type = "org.gsoft.phoenix.util.jpa.GenericEnumUserType", parameters={
			@Parameter(name = "enumClass", value = "org.gsoft.phoenix.domain.loan.LoanType")
	})
    @Column( name = "LoanTypeID" )
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getMaximumLoanTerm() {
		return maximumLoanTerm;
	}
	public void setMaximumLoanTerm(Integer maximumLoanTerm) {
		this.maximumLoanTerm = maximumLoanTerm;
	}
}