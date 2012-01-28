package org.gsoft.phoenix.service.security;

import org.gsoft.phoenix.domain.security.SystemUser;

public interface SystemUserManagementService {
	public SystemUser registerNewUser(SystemUser newUser);
}
