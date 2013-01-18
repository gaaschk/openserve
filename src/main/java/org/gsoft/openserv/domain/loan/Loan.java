package org.gsoft.openserv.domain.loan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.OpenServDomainObject;
import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;
import org.gsoft.openserv.util.ApplicationContextLocator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Months;

@Entity
@RulesEngineEntity
public class Loan extends OpenServDomainObject{
	private static final long serialVersionUID = 7541874847320220624L;
	private Long loanID;
	private Date servicingStartDate;
	private Long effectiveLoanTypeProfileID;
	private Integer startingPrincipal;
	private BigDecimal startingInterest;
	private Integer startingFees;
	private Integer startingLoanTerm;
	//Enumerations
	private LoanType loanType;
	//Relationships
	private Person borrower;
	private List<Disbursement> disbursements;

	private Integer minimumPaymentAmount;
	private Date repaymentStartDate;
	private Date firstDueDate;
	private Date initialDueDate;
	private Date lastPaidDate;
	private Date currentUnpaidDueDate;
	private Date nextDueDate;
	
	private boolean interestRateCurrent = false;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	public Date getServicingStartDate() {
		return (servicingStartDate==null)?null:(Date)servicingStartDate.clone();
	}
	public void setServicingStartDate(Date servicingStartDate) {
		this.servicingStartDate = (servicingStartDate==null)?null:(Date)servicingStartDate.clone();
	}
	@Type( type = "org.gsoft.openserv.util.jpa.GenericEnumUserType", parameters={
			@Parameter(name = "enumClass", value = "org.gsoft.openserv.domain.loan.LoanType")
	})
    @Column( name = "LoanTypeID" )
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
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
	public Integer getStartingLoanTerm() {
		return startingLoanTerm;
	}
	public void setStartingLoanTerm(Integer startingLoanTerm) {
		this.startingLoanTerm = startingLoanTerm;
	}
	public Integer getMinimumPaymentAmount() {
		return minimumPaymentAmount;
	}
	public void setMinimumPaymentAmount(Integer minimumPaymentAmount) {
		this.minimumPaymentAmount = minimumPaymentAmount;
	}
	public Date getRepaymentStartDate() {
		return (repaymentStartDate==null)?null:(Date)repaymentStartDate.clone();
	}
	public void setRepaymentStartDate(Date repaymentStartDate) {
		this.repaymentStartDate = (repaymentStartDate==null)?null:(Date)repaymentStartDate.clone();
	}
	public Date getFirstDueDate() {
		return (firstDueDate==null)?null:(Date)firstDueDate.clone();
	}
	public void setFirstDueDate(Date firstDueDate) {
		this.firstDueDate = (firstDueDate==null)?null:(Date)firstDueDate.clone();;
	}
	public Date getInitialDueDate() {
		return (initialDueDate==null)?null:(Date)initialDueDate.clone();
	}
	public void setInitialDueDate(Date initialDueDate) {
		this.initialDueDate = (initialDueDate==null)?null:(Date)initialDueDate.clone();
	}
	public Date getLastPaidDate() {
		return (lastPaidDate==null)?null:(Date)lastPaidDate.clone();
	}
	public void setLastPaidDate(Date lastPaidDate) {
		this.lastPaidDate = (lastPaidDate==null)?null:(Date)lastPaidDate.clone();
	}
	public Date getCurrentUnpaidDueDate() {
		return (currentUnpaidDueDate==null)?null:(Date)currentUnpaidDueDate.clone();
	}
	public void setCurrentUnpaidDueDate(Date currentUnpaidDueDate) {
		this.currentUnpaidDueDate = (currentUnpaidDueDate==null)?null:(Date)currentUnpaidDueDate.clone();
	}
	@Transient
	public Date getNextDueDate() {
		if(this.nextDueDate == null){
			this.nextDueDate = this.getCurrentUnpaidDueDate();
			if(ApplicationContextLocator.getApplicationContext() != null){
				SystemSettingsLogic systemSettings = ApplicationContextLocator.getApplicationContext().getBean(SystemSettingsLogic.class);
				DateTime systemDate = new DateTime(systemSettings.getCurrentSystemDate());
				if(this.nextDueDate.before(systemDate.toDate())){
					nextDueDate = new DateTime(this.getInitialDueDate()).plusMonths(this.getUsedLoanTerm()).toDate();
				}
			}
		}
		return (nextDueDate == null)?null:(Date)nextDueDate.clone();
	}
	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = (nextDueDate==null)?null:(Date)nextDueDate.clone();
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

	@Transient
	public Integer getUsedLoanTerm(){
		int termPassed = 0;
		if(this.getInitialDueDate() != null){
			SystemSettingsLogic systemSettings = ApplicationContextLocator.getApplicationContext().getBean(SystemSettingsLogic.class);
			if(this.getInitialDueDate().before(systemSettings.getCurrentSystemDate())){
				termPassed = Months.monthsBetween(new DateTime(this.getInitialDueDate()), new DateTime(systemSettings.getCurrentSystemDate())).getMonths();
			}
		}
		return termPassed;
	}
	
	@Transient
	public Integer getRemainingLoanTerm(){
		if(this.getStartingLoanTerm() == null){return 0;}
		return this.getStartingLoanTerm() - this.getUsedLoanTerm();
	}
	
	public boolean isInterestRateCurrent() {
		return interestRateCurrent;
	}
	public void setInterestRateCurrent(boolean interestRateCurrent) {
		this.interestRateCurrent = interestRateCurrent;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getLoanID();
	}
}
