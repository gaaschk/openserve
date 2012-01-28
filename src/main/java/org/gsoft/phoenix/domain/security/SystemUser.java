package org.gsoft.phoenix.domain.security;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="SecSystemUser")
public class SystemUser extends Principal{
	private static final long serialVersionUID = 3385825797666879185L;
	private String username;
	private String password;
	//Relationships
	private List<SystemRole> assignedRoles;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="SecAssignedRole",
			joinColumns=@JoinColumn(name="UserPrincipalID"),
			inverseJoinColumns=@JoinColumn(name="RolePrincipalID")
			)
	public List<SystemRole> getAssignedRoles() {
		return assignedRoles;
	}
	public void setAssignedRoles(List<SystemRole> assignedRoles) {
		this.assignedRoles = assignedRoles;
	}
	
	@Transient
	public String getPasswordSalt(){
		return this.getUsername();
	}
}
