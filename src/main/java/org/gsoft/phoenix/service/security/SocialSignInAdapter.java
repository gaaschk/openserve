package org.gsoft.phoenix.service.security;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

@Service
public class SocialSignInAdapter implements SignInAdapter{
	@Resource
	private ApplicationContext applicationContext;
	
	private RequestCache requestCache;
	
	
	@Inject
	public SocialSignInAdapter(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

	@Override
	public String signIn(final String localUserId, Connection<?> connection, NativeWebRequest request) {
		Object objAuthServ = applicationContext.getBean("authenticationManager");
		final AuthenticationManager authServ = (AuthenticationManager)objAuthServ;
		SecurityContextHolder.getContext().setAuthentication(authServ.authenticate(new SkipCheckUsernamePasswordAuthenticationToken(localUserId)));
		return extractOriginalUrl(request);
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
