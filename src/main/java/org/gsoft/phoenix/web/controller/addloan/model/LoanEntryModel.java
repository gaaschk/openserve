package org.gsoft.phoenix.web.controller.addloan.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gsoft.phoenix.domain.loan.LoanType;
import org.gsoft.phoenix.util.formatter.CurrencyInPenniesFormat;


public class LoanEntryModel implements Serializable{
	private static final long serialVersionUID = -4761897960840405245L;

	private Long loanID;
	private LoanType loanType;
	private PersonModel person;
	private Integer startingPrincipal;
	private BigDecimal startingInterest;
	private Integer startingFees;
	private List<DisbursementModel> addedDisbursements;
	private DisbursementModel newDisbursement;
	
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
	@CurrencyInPenniesFormat
	public Integer getStartingPrincipal() {
		return startingPrincipal;
	}
	public void setStartingPrincipal(Integer startingPrincipal) {
		this.startingPrincipal = startingPrincipal;
	}
	@CurrencyInPenniesFormat
	public BigDecimal getStartingInterest() {
		return startingInterest;
	}
	public void setStartingInterest(BigDecimal startingInterest) {
		this.startingInterest = startingInterest;
	}
	@CurrencyInPenniesFormat
	public Integer getStartingFees() {
		return startingFees;
	}
	public void setStartingFees(Integer startingFees) {
		this.startingFees = startingFees;
	}
	public List<DisbursementModel> getAddedDisbursements() {
		return addedDisbursements;
	}
	public void setAddedDisbursements(List<DisbursementModel> addedDisbursements) {
		this.addedDisbursements = addedDisbursements;
	}
	public DisbursementModel getNewDisbursement() {
		return newDisbursement;
	}
	public void setNewDisbursement(DisbursementModel newDisbursement) {
		this.newDisbursement = newDisbursement;
	}
}
