package org.gsoft.openserv.web.social.controller;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;
/**
 * Had to create this class so that we can override the RequestMapping annotations.
 * 
 * @author gaaschk
 *
 */
public class CustomProviderSignInController extends ProviderSignInController {
	
	@Inject
	public CustomProviderSignInController(
			ConnectionFactoryLocator connectionFactoryLocator,
			UsersConnectionRepository usersConnectionRepository, SignInAdapter signinAdapter) {
		super(connectionFactoryLocator, usersConnectionRepository, signinAdapter);
		super.setSignInUrl("/web/signin");
		super.setSignUpUrl("/web/signup");
	}

	@Override
	public RedirectView signIn(@PathVariable String providerId, NativeWebRequest request) {
		return super.signIn(providerId, request);
 	}

	@Override
	public RedirectView oauth1Callback(@PathVariable String providerId, NativeWebRequest request) {
		return super.oauth1Callback(providerId, request);
	}

	@Override
	public RedirectView oauth2Callback(@PathVariable String providerId, @RequestParam("code") String code, NativeWebRequest request) {
		return super.oauth2Callback(providerId, code, request);
	}

	@Override
	public RedirectView canceledAuthorizationCallback() {
		return super.canceledAuthorizationCallback();
	}
	
	
}
