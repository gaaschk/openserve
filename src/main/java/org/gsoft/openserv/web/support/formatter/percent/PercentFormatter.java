package org.gsoft.openserv.web.support.formatter.percent;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.number.AbstractNumberFormatter;

public class PercentFormatter extends AbstractNumberFormatter{
	public PercentFormatter(){
	}

	@Override
	public Number parse(String text, Locale locale) throws ParseException {
		text = text.replaceAll("%", "");
		Number value = super.parse(text, locale);
		BigDecimal bdAmount = new BigDecimal(value.toString());
		bdAmount = bdAmount.divide(new BigDecimal(100));
		return bdAmount;
	}

	@Override
	public String print(Number number, Locale locale) {
		BigDecimal value = new BigDecimal(number.toString());
		value = value.multiply(new BigDecimal(100));
		return this.getNumberFormat(locale).format(value)+"%";
	}
	
	@Override
	protected NumberFormat getNumberFormat(Locale locale) {
		DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(locale);
		format.setParseBigDecimal(true);
		return format;
	}
}
