package org.gsoft.openserv.web.person;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class PersonSearchCriteria implements Serializable{
	private static final long serialVersionUID = 16618634835756476L;

	private String ssn;

	public PersonSearchCriteria(){}
	
	@NotNull
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		ssn = ssn.replaceAll("-", "");
		ssn = ssn.replaceAll("_", "");
		this.ssn = ssn;
	}
	
}
