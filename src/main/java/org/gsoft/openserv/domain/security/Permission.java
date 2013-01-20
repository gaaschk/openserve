package org.gsoft.openserv.domain.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;

@Entity
@Table(name="SecPermission")
public class Permission extends PersistentDomainObject{
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
	@Override
	@Transient
	public Long getID() {
		return this.getPermissionID();
	}
}
