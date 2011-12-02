package org.gsoft.phoenix.util;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class CurrencyInPenniesConverterFactory implements ConverterFactory<String, Number>{
	public <T extends Number> Converter<String, T> getConverter(Class<T> targetType){
		return new StringToCurrencyConverter<T>(targetType);
	}
	
	private final class StringToCurrencyConverter<T extends Number> implements Converter<String, T>{
        private Class<T> currencyType;

        public StringToCurrencyConverter(Class<T> currencyType) {
        	this.currencyType = currencyType;
        }

        public T convert(String source) {
        	source = source.replace("$", "");
        	source = source.replace("US", "");
        	source = source.replaceAll(",", "");
        	BigDecimal value = new BigDecimal(source.trim());
        	value = value.multiply(new BigDecimal(100));
        	if(currencyType == Integer.class)
        		return (T)new Integer(value.intValue());
        	return (T)value;
        }
	} 
}
