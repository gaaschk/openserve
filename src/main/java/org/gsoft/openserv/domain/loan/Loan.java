package org.gsoft.openserv.domain.loan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.gsoft.openserv.buslogic.amortization.LoanTermCalculator;
import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.domain.amortization.LoanAmortizationSchedule;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class Loan extends PersistentDomainObject{
	private static final long serialVersionUID = 7541874847320220624L;
	private Long loanID;
	private Long lenderID;
	private Date servicingStartDate;
	private Integer startingPrincipal;
	private BigDecimal startingInterest;
	private Integer startingFees;
	private Integer initialUsedLoanTerm;
	private Date firstDueDate;
	private Date initialDueDate;
	private Date repaymentStartDate;
	//Enumerations
	private LoanType loanType;
	//Relationships
	private Account account;
	private Person borrower;
	private List<Disbursement> disbursements;
	private LoanAmortizationSchedule currentAmortizationSchedule;

	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	public Long getLenderID() {
		return lenderID;
	}
	public void setLenderID(Long lenderID) {
		this.lenderID = lenderID;
	}
	public Date getServicingStartDate() {
		return (servicingStartDate==null)?null:(Date)servicingStartDate.clone();
	}
	public void setServicingStartDate(Date servicingStartDate) {
		this.servicingStartDate = (servicingStartDate==null)?null:(Date)servicingStartDate.clone();
	}
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn( name = "LoanTypeID" )
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	@ManyToOne
	@JoinColumn(name = "AccountID")
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	@OneToOne
	@JoinColumn(name="CurrentLoanAmortizationScheduleID")
	public LoanAmortizationSchedule getCurrentAmortizationSchedule() {
		return currentAmortizationSchedule;
	}
	public void setCurrentAmortizationSchedule(
			LoanAmortizationSchedule currentAmortizationSchedule) {
		this.currentAmortizationSchedule = currentAmortizationSchedule;
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
	public Integer getInitialUsedLoanTerm() {
		return initialUsedLoanTerm;
	}
	public void setInitialUsedLoanTerm(Integer initialUsedLoanTerm) {
		this.initialUsedLoanTerm = initialUsedLoanTerm;
	}
	public Date getFirstDueDate() {
		return (firstDueDate==null)?null:(Date)firstDueDate.clone();
	}
	public void setFirstDueDate(Date firstDueDate) {
		this.firstDueDate = (firstDueDate==null)?null:(Date)firstDueDate.clone();
	}
	public Date getInitialDueDate() {
		return (initialDueDate==null)?null:(Date)initialDueDate.clone();
	}
	public void setInitialDueDate(Date initialDueDate) {
		this.initialDueDate = (initialDueDate==null)?null:(Date)initialDueDate.clone();
	}
	@OneToMany(mappedBy="loan", cascade={CascadeType.ALL})
	public List<Disbursement> getDisbursements() {
		return disbursements;
	}
	public void setDisbursements(List<Disbursement> disbursements) {
		this.disbursements = disbursements;
	}
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="BorrowerPersonID")
	public Person getBorrower() {
		return borrower;
	}
	public void setBorrower(Person borrower) {
		this.borrower = borrower;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getLoanID();
	}
}
