package org.gsoft.phoenix;

import javax.annotation.Resource;

import org.gsoft.phoenix.service.security.PhoenixAuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Authenticator {
	@Resource
	private PhoenixAuthenticationService phoenixAuthenticationService;

	public void authenticate() {
		Authentication request = new UsernamePasswordAuthenticationToken("admin", "password");
		Authentication result = phoenixAuthenticationService.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(result);
	}
}
