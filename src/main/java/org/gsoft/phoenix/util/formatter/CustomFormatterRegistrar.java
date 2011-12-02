package org.gsoft.phoenix.util.formatter;

import javax.annotation.Resource;

import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

public class CustomFormatterRegistrar implements FormatterRegistrar {
	@Resource
	private CurrencyInPenniesFormatAnnotationFormatterFactory currencyInPenniesFormatterFactory;
	
	@Override
	public void registerFormatters(FormatterRegistry registry) {
		registry.addFormatterForFieldAnnotation(currencyInPenniesFormatterFactory);
	}

}
