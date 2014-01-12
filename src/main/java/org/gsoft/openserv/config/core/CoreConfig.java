package org.gsoft.openserv.config.core;

import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@ComponentScan(basePackages={"org.gsoft.openserv"}
		,excludeFilters = { @Filter(
			type = FilterType.CUSTOM,
			value = { WebConfigPackageFilter.class }),
			@Filter(
					type = FilterType.CUSTOM,
					value = { WebPackageFilter.class })}
)
public class CoreConfig {
    @Bean
    public ObjectMapper objectMapper(){
    	return new ObjectMapper();
    }
    
	@Bean
	public RequestCache requestCache() {
		return new HttpSessionRequestCache();
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
}

class WebConfigPackageFilter extends RegexPatternTypeFilter
{
    public WebConfigPackageFilter() {
        super(Pattern.compile("org\\.gsoft\\.openserv\\.config\\.web\\..*"));
    }
}

class WebPackageFilter extends RegexPatternTypeFilter
{
    public WebPackageFilter() {
        super(Pattern.compile("org\\.gsoft\\.openserv\\.web\\..*"));
    }
}
