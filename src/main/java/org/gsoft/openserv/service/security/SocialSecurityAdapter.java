package org.gsoft.openserv.service.security;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gsoft.openserv.domain.security.SystemUser;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

@Service
public class SocialSecurityAdapter implements SignInAdapter{
	@Resource
	private ApplicationContext applicationContext;
	
	private RequestCache requestCache;
	
	
	@Inject
	public SocialSecurityAdapter(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

	@Override
	public String signIn(final String localUserId, Connection<?> connection, NativeWebRequest request) {
		this.signIn(localUserId);
		return extractOriginalUrl(request);
	}
	
	public void signIn(final String username, final String password){
		Object objAuthServ = applicationContext.getBean("authenticationManager");
		final AuthenticationManager authServ = (AuthenticationManager)objAuthServ;
		SecurityContextHolder.getContext().setAuthentication(authServ.authenticate(new UsernamePasswordAuthenticationToken(username,password)));
	}
	
	public void signIn(final String localUserId){
		Object objAuthServ = applicationContext.getBean("authenticationManager");
		final AuthenticationManager authServ = (AuthenticationManager)objAuthServ;
		SecurityContextHolder.getContext().setAuthentication(authServ.authenticate(new SkipCheckUsernamePasswordAuthenticationToken(localUserId)));
	}

	public SystemUser registerNewUser(SystemUser newUser){
		SystemUserManagementService userService = (SystemUserManagementService)applicationContext.getBean("systemUserManagementService");
		SystemUser existingUser = userService.findExistingUser(newUser.getUsername());
		if(existingUser==null)existingUser=userService.registerNewUser(newUser);
		return existingUser;
	}
	
	private String extractOriginalUrl(NativeWebRequest request) {
		HttpServletRequest nativeReq = request.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse nativeRes = request.getNativeResponse(HttpServletResponse.class);
		SavedRequest saved = requestCache.getRequest(nativeReq, nativeRes);
		if (saved == null) {
			return null;
		}
		requestCache.removeRequest(nativeReq, nativeRes);
		removeAutheticationAttributes(nativeReq.getSession(false));
		return saved.getRedirectUrl();
	}

	private void removeAutheticationAttributes(HttpSession session) {
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
