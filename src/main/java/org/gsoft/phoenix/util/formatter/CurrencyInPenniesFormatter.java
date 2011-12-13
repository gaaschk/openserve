package org.gsoft.phoenix.util.formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import org.springframework.format.number.AbstractNumberFormatter;
import org.springframework.util.ClassUtils;

public class CurrencyInPenniesFormatter extends AbstractNumberFormatter{
	private static final boolean roundingModeOnDecimalFormat =
			ClassUtils.hasMethod(DecimalFormat.class, "setRoundingMode", RoundingMode.class);

	private int fractionDigits = 2;

	private RoundingMode roundingMode;

	private Currency currency;
	
	private int subPenniesPrecision = 0;
	
	public CurrencyInPenniesFormatter(int subPennyPrecision){
		this.subPenniesPrecision = subPennyPrecision;
	}

	/**
	 * Specify the desired number of fraction digits.
	 * Default is 2.
	 */
	public void setFractionDigits(int fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	/**
	 * Specify the rounding mode to use for decimal parsing.
	 * Default is {@link RoundingMode#UNNECESSARY}.
	 */
	public void setRoundingMode(RoundingMode roundingMode) {
		this.roundingMode = roundingMode;
	}

	/**
	 * Specify the currency, if known.
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}


	@Override
	public Number parse(String text, Locale locale) throws ParseException {
		text = text.replace("$", "");
		text = text.replace("US", "");
		text = text.replaceAll(",", "");
		Number amountInDollars = super.parse(text, locale);
		BigDecimal bdAmount = new BigDecimal(amountInDollars.toString());
		//is whole pennies
		if(bdAmount.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0){
			return bdAmount.intValue();
		}
		return bdAmount;
	}

	@Override
	public String print(Number number, Locale locale) {
		BigDecimal pennies = new BigDecimal(number.toString()).divide(new BigDecimal(100));
		return super.print(pennies, locale);
	}
	
	@Override
	protected NumberFormat getNumberFormat(Locale locale) {
		DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
		format.setParseBigDecimal(true);
		format.setMaximumFractionDigits(this.fractionDigits+this.subPenniesPrecision);
		format.setMinimumFractionDigits(this.fractionDigits);
		if (this.roundingMode != null && roundingModeOnDecimalFormat) {
			format.setRoundingMode(this.roundingMode);
		}
		if (this.currency != null) {
			format.setCurrency(this.currency);
		}
		return format;
	}
}
