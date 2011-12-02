package org.gsoft.phoenix.util.formatter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

public class CurrencyInPenniesFormatAnnotationFormatterFactory implements
		AnnotationFormatterFactory<CurrencyInPenniesFormat> {
	
	private final Set<Class<?>> fieldTypes; 
	
	public CurrencyInPenniesFormatAnnotationFormatterFactory(){
		Set<Class<?>> rawFieldTypes = new HashSet<Class<?>>(7);
		rawFieldTypes.add(Integer.class);
		rawFieldTypes.add(BigDecimal.class);
		this.fieldTypes = Collections.unmodifiableSet(rawFieldTypes);
	}
	
    public Set<Class<?>> getFieldTypes() {
    	return fieldTypes;
    }

    public Printer<Number> getPrinter(CurrencyInPenniesFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    public Parser<Number> getParser(CurrencyInPenniesFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    private Formatter<Number> configureFormatterFrom(CurrencyInPenniesFormat annotation,
                                                     Class<?> fieldType) {
    	return new CurrencyInPenniesFormatter();
    }
}
