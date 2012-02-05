package org.gsoft.openserv.web.converters.model;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.DailyRateQuote;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.repositories.rates.DailyRateQuoteRepository;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.gsoft.openserv.web.models.RateModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RateModelToDailyRateQuoteConverter implements Converter<RateModel, DailyRateQuote>{
	@Resource
	private DailyRateQuoteRepository rateQuoteRepository;
	@Resource
	private RateRepository rateRepository;
	
	@Override
	public DailyRateQuote convert(RateModel source){
		String symbol = source.getSymbol();
		Date quoteDate = source.getQuoteDate();
		DailyRateQuote quote = rateQuoteRepository.findDailyRateQuoteBySymbol(symbol, quoteDate);
		if(quote == null){
			quote = new DailyRateQuote();
			Rate rate = rateRepository.findRateBySymbol(symbol);
			quote.setRate(rate);
			quote.setQuoteDate(source.getQuoteDate());
		}
		quote.setValue(source.getValue());
		return quote;
	}
}
