package org.gsoft.openserv.service.security;

import org.gsoft.openserv.domain.security.SystemUser;

public interface SystemUserManagementService {
	public SystemUser registerNewUser(SystemUser newUser);
	public SystemUser findExistingUser(String username);
}
