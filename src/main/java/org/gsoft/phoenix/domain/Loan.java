package org.gsoft.phoenix.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Loan implements PhoenixDomainObject{
	private static final long serialVersionUID = 7541874847320220624L;
	private Long loanID;
	private Long borrowerPersonID;
	private Integer startingPrincipal;
	private BigDecimal startingInterest;
	private Integer startingFees;
	private Integer currentPrincipal;
	private BigDecimal currentInterest;
	private Integer currentFees;
	private BigDecimal margin;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	public Long getBorrowerPersonID() {
		return borrowerPersonID;
	}
	public void setBorrowerPersonID(Long borrowerPersonID) {
		this.borrowerPersonID = borrowerPersonID;
	}
	public Integer getStartingPrincipal() {
		return startingPrincipal;
	}
	public void setStartingPrincipal(Integer startingPrincipal) {
		this.startingPrincipal = startingPrincipal;
	}
	public BigDecimal getStartingInterest() {
		return startingInterest;
	}
	public void setStartingInterest(BigDecimal startingInterest) {
		this.startingInterest = startingInterest;
	}
	public Integer getStartingFees() {
		return startingFees;
	}
	public void setStartingFees(Integer startingFees) {
		this.startingFees = startingFees;
	}
	public Integer getCurrentPrincipal() {
		return currentPrincipal;
	}
	public void setCurrentPrincipal(Integer currentPrincipal) {
		this.currentPrincipal = currentPrincipal;
	}
	public BigDecimal getCurrentInterest() {
		return currentInterest;
	}
	public void setCurrentInterest(BigDecimal currentInterest) {
		this.currentInterest = currentInterest;
	}
	public Integer getCurrentFees() {
		return currentFees;
	}
	public void setCurrentFees(Integer currentFees) {
		this.currentFees = currentFees;
	}
	public BigDecimal getMargin() {
		return margin;
	}
	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}
}
