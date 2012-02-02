package org.gsoft.openserv.service.security;

import org.gsoft.openserv.domain.security.Permission;
import org.springframework.security.core.GrantedAuthority;

public class OpenServGrantedAuthority implements GrantedAuthority{
	private static final long serialVersionUID = 1L;
	private String permissionName;
	
	public OpenServGrantedAuthority(Permission permission){
		permissionName = permission.getName().trim();
	}
	
	@Override
	public String getAuthority() {
		return "PERM_"+permissionName;
	}

}
