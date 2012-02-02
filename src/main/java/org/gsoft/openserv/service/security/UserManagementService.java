package org.gsoft.openserv.service.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.security.Permission;
import org.gsoft.openserv.domain.security.SystemRole;
import org.gsoft.openserv.domain.security.SystemUser;
import org.gsoft.openserv.repositories.security.SystemRoleRepository;
import org.gsoft.openserv.repositories.security.SystemUserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("systemUserManagementService")
@Transactional(readOnly=true)
public class UserManagementService extends AbstractUserDetailsAuthenticationProvider implements SystemUserManagementService{
	@Resource
	private SystemUserRepository systemUserRepository;
	@Resource
	private SystemRoleRepository systemRoleRepository;
	

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if(authentication instanceof SkipCheckUsernamePasswordAuthenticationToken)
			return;
		SystemUser theUser = systemUserRepository.findByUsername(userDetails.getUsername());
		String clearPass = (String)authentication.getCredentials();
		String hashedPass = new Md5PasswordEncoder().encodePassword(clearPass, theUser.getPasswordSalt());
		if(!userDetails.getPassword().equals(hashedPass))
			throw new BadCredentialsException("Invalid Password");
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		return this.getUserDetails(username);
	}

	public UserDetails getUserDetails(String username){
		SystemUser systemUser = systemUserRepository.findByUsername(username);
		if(systemUser == null)throw new UsernameNotFoundException("User not found.");
		List<Permission> permissions = systemUserRepository.findAllPermissionsForUser(username);
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(Permission permission:permissions){
			authorities.add(new PhoenixGrantedAuthority(permission));
		}
		User user = new User(systemUser.getUsername().trim(),systemUser.getPassword().trim(),authorities);
		return user;
	}
	
	@Transactional
	public SystemUser registerNewUser(SystemUser newUser){
		if("admin".equalsIgnoreCase(newUser.getUsername())){
			List<SystemRole> roles = newUser.getAssignedRoles();
			if(roles == null){
				roles = new ArrayList<SystemRole>();
				newUser.setAssignedRoles(roles);
			}
			roles.add(systemRoleRepository.findRoleByRoleName("admin"));
		}
		return this.systemUserRepository.save(newUser);
	}
	
	public SystemUser findExistingUser(String username){
		return systemUserRepository.findByUsername(username);
	}
}
