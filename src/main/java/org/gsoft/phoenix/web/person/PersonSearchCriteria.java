package org.gsoft.phoenix.web.person;

import java.io.Serializable;

public class PersonSearchCriteria implements Serializable{
	private static final long serialVersionUID = 16618634835756476L;

	private String ssn;

	public PersonSearchCriteria(){}
	
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		ssn = ssn.replaceAll("-", "");
		this.ssn = ssn;
	}
	
}
