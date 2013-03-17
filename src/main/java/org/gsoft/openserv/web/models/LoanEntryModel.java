package org.gsoft.openserv.web.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.lender.Lender;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.web.formatter.currency.CurrencyInPenniesFormat;
import org.springframework.format.annotation.DateTimeFormat;


public class LoanEntryModel implements Serializable{
	private static final long serialVersionUID = -4761897960840405245L;

	private Long loanID;
	private String selectedLoanProgramID;
	private List<LoanProgram> loanProgramList;
	private String selectedLenderID;
	private List<Lender> lenderList;
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
	public String getSelectedLoanProgramID() {
		return selectedLoanProgramID;
	}
	public void setSelectedLoanProgramID(String loanProgramID) {
		this.selectedLoanProgramID = loanProgramID;
	}
	public List<LoanProgram> getLoanProgramList() {
		return loanProgramList;
	}
	public void setLoanProgramList(List<LoanProgram> loanProgramList) {
		this.loanProgramList = loanProgramList;
	}
	public String getSelectedLenderID() {
		return selectedLenderID;
	}
	public void setSelectedLenderID(String selectedLenderID) {
		this.selectedLenderID = selectedLenderID;
	}
	public List<Lender> getLenderList() {
		return lenderList;
	}
	public void setLenderList(List<Lender> lenderList) {
		this.lenderList = lenderList;
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
