package org.gsoft.openserv.util.support.formatter;

import javax.annotation.Resource;

import org.gsoft.openserv.web.support.formatter.currency.CurrencyInPenniesFormatAnnotationFormatterFactory;
import org.gsoft.openserv.web.support.formatter.percent.PercentFormatAnnotationFormatterFactory;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

public class CustomFormatterRegistrar implements FormatterRegistrar {
	@Resource
	private CurrencyInPenniesFormatAnnotationFormatterFactory currencyInPenniesFormatterFactory;
	@Resource
	private PercentFormatAnnotationFormatterFactory percentFormatterFactory;
	
	@Override
	public void registerFormatters(FormatterRegistry registry) {
		registry.addFormatterForFieldAnnotation(currencyInPenniesFormatterFactory);
		registry.addFormatterForFieldAnnotation(percentFormatterFactory);
	}

}
