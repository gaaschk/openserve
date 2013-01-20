package org.gsoft.openserv.domain.amortization;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;

@Entity
public class LoanAmortizationSchedule extends PersistentDomainObject {
	private static final long serialVersionUID = -248336175803533637L;

	private Long loanAmortizationScheduleID;
	private Long loanID;
	private List<AmortizationLoanPayment> amortizationLoanPayments;
	private AmortizationSchedule amortizationSchedule;
	
	public void addAmortizationLoanPayment(AmortizationLoanPayment amortizationLoanPayment){
		if(this.amortizationLoanPayments==null){
			amortizationLoanPayments = new ArrayList<AmortizationLoanPayment>();
		}
		amortizationLoanPayments.add(amortizationLoanPayment);
		amortizationLoanPayment.setLoanAmortizationSchedule(this);
	}
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getLoanAmortizationScheduleID() {
		return loanAmortizationScheduleID;
	}
	public void setLoanAmortizationScheduleID(Long loanAmortizationScheduleID) {
		this.loanAmortizationScheduleID = loanAmortizationScheduleID;
	}
	public Long getLoanID() {
		return loanID;
	}
	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}
	@OneToMany(mappedBy="loanAmortizationSchedule", cascade=CascadeType.ALL)
	public List<AmortizationLoanPayment> getAmortizationLoanPayments() {
		return amortizationLoanPayments;
	}
	public void setAmortizationLoanPayments(List<AmortizationLoanPayment> amortizationLoanPayments) {
		this.amortizationLoanPayments = amortizationLoanPayments;
	}
	@ManyToOne
	@JoinColumn(name="AmortizationScheduleID")
	public AmortizationSchedule getAmortizationSchedule() {
		return amortizationSchedule;
	}
	public void setAmortizationSchedule(AmortizationSchedule amortizationSchedule) {
		this.amortizationSchedule = amortizationSchedule;
	}
	@Transient
	public int getTotalNumberOfPayment(){
		int count = 0;
		for(AmortizationLoanPayment payment:this.getAmortizationLoanPayments()){
			count += payment.getPaymentCount();
		}
		return count;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getLoanAmortizationScheduleID();
	}
}
