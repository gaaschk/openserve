package org.gsoft.phoenix.domain.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.gsoft.phoenix.domain.PhoenixDomainObject;

@Entity
@Table(name="SecPermission")
public class Permission implements PhoenixDomainObject{
	private static final long serialVersionUID = -4606078266819681320L;
	private Long permissionID;
	private String name;
	
	@Id
    @GeneratedValue( strategy=GenerationType.AUTO )
	public Long getPermissionID() {
		return permissionID;
	}
	public void setPermissionID(Long permissionID) {
		this.permissionID = permissionID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}