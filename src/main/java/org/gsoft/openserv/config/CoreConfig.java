package org.gsoft.openserv.config;

import org.gsoft.openserv.web.support.converter.CurrencyInPenniesConverterFactory;
import org.gsoft.openserv.web.support.formatter.currency.CurrencyInPenniesFormatAnnotationFormatterFactory;
import org.gsoft.openserv.web.support.formatter.percent.PercentFormatAnnotationFormatterFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"org.gsoft.openserv.buslogic","org.gsoft.openserv.rulesengine"})
public class CoreConfig {
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
	
	@Bean
	public ConversionService conversionService() {
		FormattingConversionService conversionService = new DefaultFormattingConversionService(true);
		conversionService.addFormatterForFieldAnnotation(new CurrencyInPenniesFormatAnnotationFormatterFactory());
		conversionService.addFormatterForFieldAnnotation(new PercentFormatAnnotationFormatterFactory());
		conversionService.addConverterFactory(new CurrencyInPenniesConverterFactory());
		return conversionService;
	}

	@Bean
	public RequestCache requestCache() {
		return new HttpSessionRequestCache();
	}
}
