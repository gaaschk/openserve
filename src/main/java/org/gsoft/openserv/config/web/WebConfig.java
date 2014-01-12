package org.gsoft.openserv.config.web;

import org.gsoft.openserv.config.core.CoreConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages={"org.gsoft.openserv"})
@Import(CoreConfig.class)
public class WebConfig extends RepositoryRestMvcConfiguration{
	@Bean
	public FormattingConversionServiceFactoryBean conversionServiceFactory(){
		return new FormattingConversionServiceFactoryBean();
	}
	
	@Bean
	public FormattingConversionService customConversionService(){
		return conversionServiceFactory().getObject();
	}
}
