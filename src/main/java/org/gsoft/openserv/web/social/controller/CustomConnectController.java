package org.gsoft.openserv.web.social.controller;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

public class CustomConnectController extends ConnectController {
	
	@Inject
	public CustomConnectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		super(connectionFactoryLocator, connectionRepository);
	}

	@Override
	public String connectionStatus(NativeWebRequest request, Model model) {
		return super.connectionStatus(request, model);
	}

	@Override
	public String connectionStatus(@PathVariable String providerId, NativeWebRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth==null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser"))
			return "redirect:/web/signin";
		return super.connectionStatus(providerId, request, model);
	}

	@Override
	public RedirectView connect(@PathVariable String providerId, NativeWebRequest request) {
		return super.connect(providerId, request);
	}

	@Override
	public RedirectView oauth1Callback(@PathVariable String providerId, NativeWebRequest request) {
		return super.oauth1Callback(providerId, request);
	}

	@Override
	public RedirectView oauth2Callback(@PathVariable String providerId, NativeWebRequest request) {
		return super.oauth2Callback(providerId, request);
	}

	@Override
	public RedirectView removeConnections(@PathVariable String providerId, NativeWebRequest request) {
		return super.removeConnections(providerId, request);
	}

	@Override
	public RedirectView removeConnection(@PathVariable String providerId, @PathVariable String providerUserId, NativeWebRequest request) {
		return super.removeConnection(providerId, providerUserId, request);
	}
}
