package org.gsoft.phoenix.service.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Hex;
import org.gsoft.phoenix.domain.security.Permission;
import org.gsoft.phoenix.domain.security.SystemUser;
import org.gsoft.phoenix.repositories.security.SystemUserRepository;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class PhoenixAuthenticationService extends AbstractUserDetailsAuthenticationProvider{
	@Resource
	private SystemUserRepository systemUserRepository;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if(authentication instanceof SkipCheckUsernamePasswordAuthenticationToken)
			return;
		String clearPass = (String)authentication.getCredentials();
		MessageDigest passwordHasher;
		try {
			passwordHasher = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new AuthenticationServiceException("Unable to initial password hasher. ", e);
		}
		String hashedPass = Hex.encodeHexString(passwordHasher.digest(clearPass.getBytes()));
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
}
