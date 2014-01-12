package org.gsoft.openserv.config.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gsoft.openserv.service.security.SystemUserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 8)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    private static Log logger = LogFactory.getLog(SecurityConfig.class);

    @Autowired
	public SystemUserManagementService systemUserManagementService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		logger.info("Setting the authenticationProvider for " + auth + " to " + systemUserManagementService);
		auth.authenticationProvider(systemUserManagementService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().anyRequest().permitAll();
		http.csrf().disable();
	}
}
