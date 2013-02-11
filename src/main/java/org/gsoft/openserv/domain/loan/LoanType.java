package org.gsoft.openserv.domain.loan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class LoanType extends PersistentDomainObject{
	private static final long serialVersionUID = -4023897386004756328L;
	private Long loanTypeID;
	private String name;
	private String description;

	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getLoanTypeID() {
		return loanTypeID;
	}

	public void setLoanTypeID(Long loanTypeID) {
		this.loanTypeID = loanTypeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	@Transient
	public Long getID() {
		return this.getLoanTypeID();
	}

}
