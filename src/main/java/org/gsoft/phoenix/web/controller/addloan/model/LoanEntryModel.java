package org.gsoft.phoenix.web.controller.addloan.model;

import java.math.BigDecimal;

import org.gsoft.phoenix.domain.loan.LoanType;


public class LoanEntryModel {
	private Long loanID;
	private LoanType loanType;
	private PersonModel person;
	private Integer startingPrincipal;
	private BigDecimal startingInterest;
	private Integer startingFees;
	
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	public PersonModel getPerson() {
		return person;
	}
	public void setPerson(PersonModel person) {
		this.person = person;
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
}
