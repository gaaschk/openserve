package org.gsoft.openserv.web.support.formatter.currency;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

public class CurrencyInPenniesFormatAnnotationFormatterFactory implements
		AnnotationFormatterFactory<CurrencyInPenniesFormat> {
	
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(Arrays.asList(new Class<?>[] {
                Integer.class, 
                BigInteger.class }));    
        }

    public Printer<Number> getPrinter(CurrencyInPenniesFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    public Parser<Number> getParser(CurrencyInPenniesFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    private Formatter<Number> configureFormatterFrom(CurrencyInPenniesFormat annotation,
                                                     Class<?> fieldType) {
    	return new CurrencyInPenniesFormatter(annotation.subPennyPrecision());
    }
}
