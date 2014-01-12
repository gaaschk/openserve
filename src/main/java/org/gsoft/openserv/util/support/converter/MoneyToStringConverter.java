package org.gsoft.openserv.util.support.converter;

import org.gsoft.openserv.lang.Money;
import org.springframework.core.convert.converter.Converter;

public class MoneyToStringConverter implements Converter<Money, String> {

	@Override
	public String convert(Money source) {
		return source.toString();
	}

}
