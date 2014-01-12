package org.gsoft.openserv.util.support.converter;

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

        @SuppressWarnings("unchecked")
		public T convert(String source) {
        	String noUnits = source.replace("$", "");
        	String noDenom = noUnits.replace("US", "");
        	String noSeparator = noDenom.replaceAll(",", "");
        	BigDecimal value = new BigDecimal(noSeparator.trim());
        	BigDecimal penniesVal = value.multiply(new BigDecimal(100));
        	if(currencyType == Integer.class)
        		return (T)new Integer(penniesVal.intValue());
        	return (T)penniesVal;
        }
	} 
}
