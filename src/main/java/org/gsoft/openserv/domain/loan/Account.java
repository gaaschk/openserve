package org.gsoft.openserv.domain.loan;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class Account extends PersistentDomainObject{
	private static final long serialVersionUID = 3012223412962635389L;
	private Long accountID;
	private String accountNumber;
	private Long borrowerPersonID;
	private Long lenderID;
	private Long loanTypeID;
	private List<Loan> loans;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getAccountID() {
		return accountID;
	}

	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}

	public String getAccountNumber(){
		return this.accountNumber;
	}
	
	public void setAccountNumber(String accountNumber){
		this.accountNumber = accountNumber;
	}
	
	public Long getBorrowerPersonID() {
		return borrowerPersonID;
	}

	public void setBorrowerPersonID(Long borrowerPersonID) {
		this.borrowerPersonID = borrowerPersonID;
	}

	public Long getLenderID() {
		return lenderID;
	}

	public void setLenderID(Long lenderID) {
		this.lenderID = lenderID;
	}

	public Long getLoanTypeID() {
		return loanTypeID;
	}

	public void setLoanTypeID(Long loanTypeID) {
		this.loanTypeID = loanTypeID;
	}

	@OneToMany(mappedBy="account")
	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	@Transient
	@Override
	public Long getID(){
		return this.getAccountID();
	}
}
