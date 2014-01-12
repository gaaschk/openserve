package org.gsoft.openserv.web.addloan.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gsoft.openserv.lang.Money;
import org.gsoft.openserv.util.support.converter.JsonMoneyDeserializer;
import org.gsoft.openserv.web.person.model.PersonModel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public class LoanEntryModel implements Serializable{
	private static final long serialVersionUID = -4761897960840405245L;

	private String selectedLoanProgramID;
	private String selectedLenderID;
	private PersonModel person;
	private Money startingPrincipal;
	private Money startingInterest;
	private Money startingFees;
	private Date effectiveDate;
	private List<DisbursementModel> addedDisbursements;
	
	public String getSelectedLoanProgramID() {
		return selectedLoanProgramID;
	}
	public void setSelectedLoanProgramID(String loanProgramID) {
		this.selectedLoanProgramID = loanProgramID;
	}
	public String getSelectedLenderID() {
		return selectedLenderID;
	}
	public void setSelectedLenderID(String selectedLenderID) {
		this.selectedLenderID = selectedLenderID;
	}
	public PersonModel getPerson() {
		return person;
	}
	public void setPerson(PersonModel person) {
		this.person = person;
	}
	@JsonDeserialize(using=JsonMoneyDeserializer.class)
	public Money getStartingPrincipal() {
		return startingPrincipal;
	}
	public void setStartingPrincipal(Money startingPrincipal) {
		this.startingPrincipal = startingPrincipal;
	}
	@JsonDeserialize(using=JsonMoneyDeserializer.class)
	public Money getStartingInterest() {
		return startingInterest;
	}
	public void setStartingInterest(Money startingInterest) {
		this.startingInterest = startingInterest;
	}
	@JsonDeserialize(using=JsonMoneyDeserializer.class)
	public Money getStartingFees() {
		return startingFees;
	}
	public void setStartingFees(Money startingFees) {
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
}
