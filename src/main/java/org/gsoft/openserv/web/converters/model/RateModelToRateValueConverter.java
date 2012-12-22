package org.gsoft.openserv.web.converters.model;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.gsoft.openserv.repositories.rates.RateValueRepository;
import org.gsoft.openserv.web.models.RateModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RateModelToRateValueConverter implements Converter<RateModel, RateValue>{
	@Resource
	private RateValueRepository rateValueRepository;
	@Resource
	private RateRepository rateRepository;
	
	@Override
	public RateValue convert(RateModel source){
		String symbol = source.getSymbol();
		Date quoteDate = source.getQuoteDate();
		RateValue quote = rateValueRepository.findRateValueByTickerSymbol(symbol, quoteDate);
		if(quote == null){
			quote = new RateValue();
			Rate rate = rateRepository.findRateByTickerSymbol(symbol);
			quote.setRate(rate);
			quote.setRateValueDate(source.getQuoteDate());
		}
		quote.setRateValue(source.getValue());
		return quote;
	}
}
