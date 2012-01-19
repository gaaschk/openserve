package org.gsoft.phoenix.domain.loan;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.PhoenixDomainObject;
import org.gsoft.phoenix.repositories.loan.LoanEventRepository;
import org.gsoft.phoenix.util.ApplicationContextLocator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
public class Loan implements PhoenixDomainObject{
	private static final long serialVersionUID = 7541874847320220624L;
	private Long loanID;
	private LoanType loanType;
	private Long borrowerPersonID;
	private Long effectiveLoanTypeProfileID;
	private Integer startingPrincipal;
	private BigDecimal startingInterest;
	private Integer startingFees;
	private Integer currentPrincipal;
	private BigDecimal currentInterest;
	private Integer currentFees;
	private BigDecimal margin;
	private LoanEvent lastLoanEvent;
	private Integer remainingLoanTerm;
	//Relationships
	private Person borrower;
	
	private List<Disbursement> disbursements;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
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
	public Long getBorrowerPersonID() {
		return borrowerPersonID;
	}
	public void setBorrowerPersonID(Long borrowerPersonID) {
		this.borrowerPersonID = borrowerPersonID;
	}
	public Long getEffectiveLoanTypeProfileID() {
		return effectiveLoanTypeProfileID;
	}
	public void setEffectiveLoanTypeProfileID(Long effectiveLoanTypeProfileID) {
		this.effectiveLoanTypeProfileID = effectiveLoanTypeProfileID;
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
	@Transient
	public Integer getCurrentPrincipal() {
		if(currentPrincipal == null && this.getLastLoanEvent() != null){
			this.currentPrincipal = this.getLastLoanEvent().getLoanTransaction().getEndingPrincipal();
		}
		return currentPrincipal;
	}
	public void setCurrentPrincipal(Integer currentPrincipal) {
		this.currentPrincipal = currentPrincipal;
	}
	@Transient
	public BigDecimal getCurrentInterest() {
		if(currentInterest == null && this.getLastLoanEvent() != null){
			this.currentInterest = this.getLastLoanEvent().getLoanTransaction().getEndingInterest();
		}
		return currentInterest;
	}
	public void setCurrentInterest(BigDecimal currentInterest) {
		this.currentInterest = currentInterest;
	}
	@Transient
	public Integer getCurrentFees() {
		if(currentFees == null && this.getLastLoanEvent() != null){
			this.currentFees = this.getLastLoanEvent().getLoanTransaction().getEndingFees();
		}
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
	@Transient
	public LoanEvent getLastLoanEvent() {
		if(lastLoanEvent == null){
			lastLoanEvent = ApplicationContextLocator.getApplicationContext().getBean(LoanEventRepository.class).findMostRecentLoanEventWithTransaction(this.getLoanID());
		}
		return lastLoanEvent;
	}
	public void setLastLoanEvent(LoanEvent lastLoanEvent) {
		this.lastLoanEvent = lastLoanEvent;
	}
	public Integer getRemainingLoanTerm() {
		return remainingLoanTerm;
	}
	public void setRemainingLoanTerm(Integer remainingLoanTerm) {
		this.remainingLoanTerm = remainingLoanTerm;
	}
	@OneToMany(mappedBy="loan")
	public List<Disbursement> getDisbursements() {
		return disbursements;
	}
	public void setDisbursements(List<Disbursement> disbursements) {
		this.disbursements = disbursements;
	}
	@ManyToOne
	public Person getBorrower() {
		return borrower;
	}
	public void setBorrower(Person borrower) {
		this.borrower = borrower;
	}
}
