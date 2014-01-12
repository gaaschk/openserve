package org.gsoft.openserv.web.support.formatter.currency;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

import org.gsoft.openserv.lang.Money;
import org.springframework.format.Formatter;

public class MoneyFormatter implements Formatter<Money> {

	@Override
	public String print(final Money object, final Locale locale) {
		return object.toString();
	}

	@Override
	public Money parse(String text, Locale locale) throws ParseException {
		text = text
				.replace("$", "")
				.replace("US", "")
				.replaceAll(",", "");
    	BigDecimal value = new BigDecimal(text.trim());
		return Money.dollars(value);
	}

}
