package org.gsoft.openserv;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Authenticator {
	@Resource
	private ApplicationContext applicationContext;

	public void authenticate() {
		Object objAuthServ = applicationContext.getBean("authenticationManager");
		final AuthenticationManager authenticationManager = (AuthenticationManager)objAuthServ;
		Authentication request = new UsernamePasswordAuthenticationToken("admin", "password");
		Authentication result = authenticationManager.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(result);
	}
}
