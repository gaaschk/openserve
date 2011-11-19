package org.gsoft.phoenix.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;

@Entity
public class Person implements PhoenixDomainObject{
	private static final long serialVersionUID = 7097467900225137835L;
	private Long personID;
	private String ssn;
	private String firstName;
	private String lastName;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getPersonID() {
		return personID;
	}
	public void setPersonID(Long personID) {
		this.personID = personID;
	}
	@NaturalId
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
