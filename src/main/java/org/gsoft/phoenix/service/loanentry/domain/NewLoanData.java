package org.gsoft.phoenix.service.loanentry.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.loan.LoanType;

public class NewLoanData {
	private LoanType loanType;
	private Person borrower;
	private List<NewDisbursementData> disbursements;
	private Integer startingPrincipal;
	private BigDecimal startingInterest;
	private Integer startingFees;
	
	
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	public Person getBorrower() {
		return borrower;
	}
	public void setBorrower(Person borrower) {
		this.borrower = borrower;
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
	public List<NewDisbursementData> getDisbursements() {
		if(disbursements == null){
			disbursements = new ArrayList<NewDisbursementData>();
		}
		return disbursements;
	}
	public void setDisbursements(List<NewDisbursementData> disbursements) {
		this.disbursements = disbursements;
	}
}
