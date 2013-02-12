package org.gsoft.openserv.web.converters.model;

import org.gsoft.openserv.domain.rates.Rate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RateToStringConverter  implements Converter<Rate, String>{
	
	public String convert(Rate rate){
		return rate.getRateName();
	}
}
