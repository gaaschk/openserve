package org.gsoft.phoenix.service.security;

import org.gsoft.phoenix.domain.security.Permission;
import org.springframework.security.core.GrantedAuthority;

public class PhoenixGrantedAuthority implements GrantedAuthority{
	private static final long serialVersionUID = 1L;
	private String permissionName;
	
	public PhoenixGrantedAuthority(Permission permission){
		permissionName = permission.getName().trim();
	}
	
	@Override
	public String getAuthority() {
		return "PERM_"+permissionName;
	}

}
