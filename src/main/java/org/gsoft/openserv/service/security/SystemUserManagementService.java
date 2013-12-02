package org.gsoft.openserv.service.security;

import org.gsoft.openserv.domain.security.SystemUser;
import org.springframework.security.authentication.AuthenticationProvider;

public interface SystemUserManagementService extends AuthenticationProvider{
	public SystemUser registerNewUser(SystemUser newUser);
	public SystemUser findExistingUser(String username);
}
