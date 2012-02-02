package org.gsoft.openserv.service.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SkipCheckUsernamePasswordAuthenticationToken extends
		UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = -6982314479339304428L;

	public SkipCheckUsernamePasswordAuthenticationToken(Object principal) {
		super(principal, null);
	}
}
