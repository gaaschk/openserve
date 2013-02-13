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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((loanTypeID == null) ? 0 : loanTypeID.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanType other = (LoanType) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (loanTypeID == null) {
			if (other.loanTypeID != null)
				return false;
		} else if (!loanTypeID.equals(other.loanTypeID))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
