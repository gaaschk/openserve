package org.gsoft.openserv.util.support.converter;

import java.math.BigDecimal;

import org.gsoft.openserv.lang.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMoneyConverter implements Converter<String, Money> {

	@Override
	public Money convert(String source) {
		source = source
				.replace("$", "")
				.replace("US", "")
				.replaceAll(",", "");
    	BigDecimal value = new BigDecimal(source.trim());
		return Money.dollars(value);
	}

}
