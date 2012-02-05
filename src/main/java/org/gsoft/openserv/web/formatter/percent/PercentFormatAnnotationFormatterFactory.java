package org.gsoft.openserv.web.formatter.percent;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

public class PercentFormatAnnotationFormatterFactory implements
		AnnotationFormatterFactory<PercentFormat> {
	
	private final Set<Class<?>> fieldTypes; 
	
	public PercentFormatAnnotationFormatterFactory(){
		Set<Class<?>> rawFieldTypes = new HashSet<Class<?>>(7);
		rawFieldTypes.add(Integer.class);
		rawFieldTypes.add(BigDecimal.class);
		this.fieldTypes = Collections.unmodifiableSet(rawFieldTypes);
	}
	
    public Set<Class<?>> getFieldTypes() {
    	return fieldTypes;
    }

    public Printer<Number> getPrinter(PercentFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    public Parser<Number> getParser(PercentFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    private Formatter<Number> configureFormatterFrom(PercentFormat annotation,
                                                     Class<?> fieldType) {
    	return new PercentFormatter();
    }
}
