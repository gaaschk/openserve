package org.gsoft.openserv.domain.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;

@Entity
@Table(name="SecAssignedPermission")
public class AssignedPermission extends PersistentDomainObject{
	private static final long serialVersionUID = -6565031937412516537L;
	private Long assignedPermissionID;
	private Long principalID;
	private Long permissionID;
	
	@Id
    @GeneratedValue( strategy=GenerationType.AUTO )
	public Long getAssignedPermissionID() {
		return assignedPermissionID;
	}
	public void setAssignedPermissionID(Long assignedPermissionID) {
		this.assignedPermissionID = assignedPermissionID;
	}
	public Long getPrincipalID() {
		return principalID;
	}
	public void setPrincipalID(Long principalID) {
		this.principalID = principalID;
	}
	public Long getPermissionID() {
		return permissionID;
	}
	public void setPermissionID(Long permissionID) {
		this.permissionID = permissionID;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getAssignedPermissionID();
	}
}
