package org.gsoft.openserv.web.security.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.gsoft.openserv.domain.security.SystemUser;
import org.gsoft.openserv.service.security.SocialSecurityAdapter;
import org.gsoft.openserv.web.message.Message;
import org.gsoft.openserv.web.message.MessageType;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
public class SignupController {
	@Inject
	private SocialSecurityAdapter securityAdapter;
	

	@RequestMapping(value="signup", method=RequestMethod.GET)
	public SignupForm signupForm(WebRequest request) {
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
		if (connection != null) {
			request.setAttribute("message", new Message(MessageType.INFO, "Your " + StringUtils.capitalize(connection.getKey().getProviderId()) + " account is not associated with a Spring Social Showcase account. If you're new, please sign up."), WebRequest.SCOPE_REQUEST);
			return SignupForm.fromProviderUser(connection.fetchUserProfile());
		} else {
			return new SignupForm();
		}
	}

	@RequestMapping(value="signup", method=RequestMethod.POST)
	public String signup(@Valid SignupForm form, BindingResult formBinding, WebRequest request) {
		if (formBinding.hasErrors()) {
			return null;
		}
		SystemUser account = createAccount(form);
		securityAdapter.signIn(account.getUsername(), account.getPassword());
		ProviderSignInUtils.handlePostSignUp(account.getUsername(), request);
		return "redirect:/";
	}

	// internal helpers
	
	private SystemUser createAccount(SignupForm form) {
		SystemUser account = new SystemUser();
		account.setActive(true);
		account.setName(form.getFirstName() + " " + form.getLastName());
		account.setUsername(form.getUsername());
		String encodedPass = new Md5PasswordEncoder().encodePassword(form.getPassword(), account.getPasswordSalt());
		account.setPassword(encodedPass);
		securityAdapter.registerNewUser(account);
		return account;
	}

}
