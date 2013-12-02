package org.gsoft.openserv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@Import(value = { RepositoryRestMvcConfiguration.class })
public class WebConfig{

	@Bean
	public ServletContextTemplateResolver templateResolver(){
		ServletContextTemplateResolver contextTemplateResolver = new ServletContextTemplateResolver();
		contextTemplateResolver.setPrefix("/WEB-INF/templates/");
		contextTemplateResolver.setSuffix(".html");
		contextTemplateResolver.setTemplateMode("HTML5");
		return contextTemplateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine(){
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		return engine;
	}
	
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver(){
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		return resolver;
	}
}
