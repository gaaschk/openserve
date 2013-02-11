package org.gsoft.openserv.web.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.loan.LoanType;
import org.gsoft.openserv.web.formatter.currency.CurrencyInPenniesFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;


public class LoanEntryModel implements Serializable{
	private static final long serialVersionUID = -4761897960840405245L;

	private Long loanID;
	private String selectedLoanTypeID;
	private List<LoanType> loanTypeList;
	private PersonModel person;
	private Integer startingPrincipal;
	private BigDecimal startingInterest;
	private Integer startingFees;
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date effectiveDate;
	private List<DisbursementModel> addedDisbursements;
	private DisbursementModel newDisbursement;
	
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	public String getSelectedLoanTypeID() {
		return selectedLoanTypeID;
	}
	public void setSelectedLoanTypeID(String loanTypeID) {
		this.selectedLoanTypeID = loanTypeID;
	}
	public List<LoanType> getLoanTypeList() {
		return loanTypeList;
	}
	public void setLoanTypeList(List<LoanType> loanTypeList) {
		this.loanTypeList = loanTypeList;
	}
	public PersonModel getPerson() {
		return person;
	}
	public void setPerson(PersonModel person) {
		this.person = person;
	}
	
	@CurrencyInPenniesFormat
	public Integer getStartingPrincipal() {
		if(this.startingPrincipal == null && this.getAddedDisbursements() != null && this.getAddedDisbursements().size() > 0){
			int principal = 0;
			for(DisbursementModel disb:this.getAddedDisbursements()){
				if(this.getEffectiveDate() != null && !this.getEffectiveDate().before(disb.getDisbursementDate()))
					principal += disb.getDisbursementAmount();
			}
			startingPrincipal = principal;
		}
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
	public Date getEffectiveDate() {
		if(this.effectiveDate == null && this.getAddedDisbursements() != null && this.getAddedDisbursements().size() > 0){
			Date earliestDate = null;
			for(DisbursementModel disb:this.getAddedDisbursements()){
				if(earliestDate == null || earliestDate.after(disb.getDisbursementDate()))
					earliestDate = disb.getDisbursementDate();
			}
			this.effectiveDate = earliestDate;
		}
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
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
