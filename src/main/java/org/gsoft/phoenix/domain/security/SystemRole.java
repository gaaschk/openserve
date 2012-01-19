package org.gsoft.phoenix.domain.security;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="SecSystemRole")
public class SystemRole extends Principal{
	private static final long serialVersionUID = 2773104468826519908L;
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
