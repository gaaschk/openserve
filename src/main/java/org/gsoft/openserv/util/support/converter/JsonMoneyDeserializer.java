package org.gsoft.openserv.util.support.converter;

import java.io.IOException;
import java.math.BigDecimal;

import org.gsoft.openserv.lang.Money;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

public class JsonMoneyDeserializer extends StdScalarDeserializer<Money>{
	private static final long serialVersionUID = 1007719592254124270L;

	protected JsonMoneyDeserializer() {
		super(Money.class);
	}

	@Override
	public Money deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		BigDecimal value = jp.getDecimalValue();
		return Money.dollars(value);
	}

}
