package org.gsoft.phoenix.domain.loan;

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

import org.gsoft.phoenix.buslogic.system.SystemSettingsLogic;
import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.PhoenixDomainObject;
import org.gsoft.phoenix.repositories.loan.LoanEventRepository;
import org.gsoft.phoenix.rulesengine.annotation.RulesEngineEntity;
import org.gsoft.phoenix.util.ApplicationContextLocator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Months;

@Entity
@RulesEngineEntity
public class Loan extends PhoenixDomainObject{
	private static final long serialVersionUID = 7541874847320220624L;
	private Long loanID;
	private Date servicingStartDate;
	private Long effectiveLoanTypeProfileID;
	private Integer startingPrincipal;
	private BigDecimal startingInterest;
	private Integer startingFees;
	private Integer currentPrincipal;
	private BigDecimal currentInterest;
	private Integer currentFees;
	private BigDecimal margin;
	private LoanEvent lastLoanEvent;
	private Integer startingLoanTerm;
	private Integer minimumPaymentAmount;
	private Date repaymentStartDate;
	private Date firstDueDate;
	private Date initialDueDate;
	private Date lastPaidDate;
	private Date currentUnpaidDueDate;
	//Enumerations
	private LoanType loanType;
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
	public Date getServicingStartDate() {
		return servicingStartDate;
	}
	public void setServicingStartDate(Date servicingStartDate) {
		this.servicingStartDate = servicingStartDate;
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
	@Transient
	public BigDecimal getCurrentInterest() {
		if(currentInterest == null && this.getLastLoanEvent() != null){
			this.currentInterest = this.getLastLoanEvent().getLoanTransaction().getEndingInterest();
		}
		return currentInterest;
	}
	@Transient
	public Integer getCurrentFees() {
		if(currentFees == null && this.getLastLoanEvent() != null){
			this.currentFees = this.getLastLoanEvent().getLoanTransaction().getEndingFees();
		}
		return currentFees;
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
		return repaymentStartDate;
	}
	public void setRepaymentStartDate(Date repaymentStartDate) {
		this.repaymentStartDate = repaymentStartDate;
	}
	public Date getFirstDueDate() {
		return firstDueDate;
	}
	public void setFirstDueDate(Date firstDueDate) {
		this.firstDueDate = firstDueDate;
	}
	public Date getInitialDueDate() {
		return initialDueDate;
	}
	public void setInitialDueDate(Date initialDueDate) {
		this.initialDueDate = initialDueDate;
	}
	public Date getLastPaidDate() {
		return lastPaidDate;
	}
	public void setLastPaidDate(Date lastPaidDate) {
		this.lastPaidDate = lastPaidDate;
	}
	public Date getCurrentUnpaidDueDate() {
		return currentUnpaidDueDate;
	}
	public void setCurrentUnpaidDueDate(Date currentUnpaidDueDate) {
		this.currentUnpaidDueDate = currentUnpaidDueDate;
	}
	@OneToMany(mappedBy="loan")
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
			termPassed = Months.monthsBetween(new DateTime(this.getInitialDueDate()), new DateTime(systemSettings.getCurrentSystemDate())).getMonths();
		}
		return termPassed;
	}
	
	@Transient
	public Integer getRemainingLoanTerm(){
		if(this.getStartingLoanTerm() == null)return 0;
		return this.getStartingLoanTerm() - this.getUsedLoanTerm();
	}
	
	@Transient
	public Integer getTotalBalance(){
		return this.getCurrentPrincipal() + this.getCurrentFees() + this.getCurrentInterest().intValue();
	}
	
	@Override
	@Transient
	public Long getID() {
		return this.getLoanID();
	}
}
