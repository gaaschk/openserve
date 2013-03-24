package org.gsoft.openserv.web.support.converter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConversionServiceFactory;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContextScanningFormattingConversionServiceFactoryBean{
	@Resource
	private ApplicationContext applicationContext;
	@Resource
	private FormattingConversionService conversionService;
	
	@PostConstruct
	public void scanContextForFormattersAndConverters(){
		Collection<Converter> converters = applicationContext.getBeansOfType(Converter.class).values();
		Set<Converter> converterSet = new HashSet<Converter>();
		converterSet.addAll(converters);
		ConversionServiceFactory.registerConverters(converterSet, this.conversionService);
	}
}
