package org.gsoft.phoenix.config;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.gsoft.phoenix.service.security.SocialSecurityAdapter;
import org.gsoft.phoenix.web.controller.social.CustomConnectController;
import org.gsoft.phoenix.web.controller.social.CustomProviderSignInController;
import org.gsoft.phoenix.web.social.facebook.PostToWallAfterConnectInterceptor;
import org.gsoft.phoenix.web.social.twitter.TweetAfterConnectInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

@Configuration
public class SocialConfig {

	@Inject
	private DataSource dataSource;
	@Resource(name="twitter.consumerKey")
	private String twitterConsumerKey;
	@Resource(name="twitter.consumerSecret")
	private String twitterConsumerSecret;
	@Resource(name="facebook.clientId")
	private String facebookClientId;
	@Resource(name="facebook.clientSecret")
	private String facebookClientSecret;
	@Resource(name="facebook.redirectUrl")
	private String facebookRedirectUrl;

	@Bean
	@Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES) 
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(new TwitterConnectionFactory(twitterConsumerKey,twitterConsumerSecret));
		registry.addConnectionFactory(new FacebookConnectionFactory(facebookClientId,facebookClientSecret));
		return registry;
	}

	@Bean
	@Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES) 
	public UsersConnectionRepository usersConnectionRepository() {
		return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(), Encryptors.noOpText());
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public ConnectionRepository connectionRepository() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
		}
		return usersConnectionRepository().createConnectionRepository(authentication.getName());
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Facebook facebook() {
		Connection<Facebook> facebook = connectionRepository().findPrimaryConnection(Facebook.class);
		return facebook != null ? facebook.getApi() : new FacebookTemplate();
	}
	
	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Twitter twitter() {
		Connection<Twitter> twitter = connectionRepository().findPrimaryConnection(Twitter.class);
		return twitter != null ? twitter.getApi() : new TwitterTemplate();
	}
	
	@Bean
	public ConnectController connectController() {
		ConnectController connectController = new CustomConnectController(connectionFactoryLocator(), connectionRepository());
		connectController.addInterceptor(new PostToWallAfterConnectInterceptor());
		connectController.addInterceptor(new TweetAfterConnectInterceptor());
		return connectController;
	}

	@Bean
	public ProviderSignInController providerSignInController(RequestCache requestCache) {
		ProviderSignInController controller = new CustomProviderSignInController(connectionFactoryLocator(), usersConnectionRepository(), new SocialSecurityAdapter(requestCache));
		controller.setApplicationUrl(facebookRedirectUrl);
		return controller;
	}

}