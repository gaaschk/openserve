package org.gsoft.openserv.web.models;

import java.io.Serializable;

public class PersonModel implements Serializable {
	private static final long serialVersionUID = -183900265755869L;
	private Long personID;
	private String ssn;
	private String firstName;
	private String lastName;

	public Long getPersonID() {
		return personID;
	}

	public void setPersonID(Long personID) {
		this.personID = personID;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		ssn = ssn.replaceAll("-", "");
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
