package org.gsoft.openserv.web.converters.model;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.gsoft.openserv.web.models.RateModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RateModelToRateConverter implements Converter<RateModel, Rate>{
	@Resource
	private RateRepository rateRepository;

	@Override
	public Rate convert(RateModel source) {
		Rate rate = null;
		if(source.getRateID() != null){
			rate = rateRepository.findOne(source.getRateID());
		}
		else{
			rate = new Rate();
			rateRepository.save(rate);
		}
		rate.setTickerSymbol(source.getSymbol());
		rate.setRateName(source.getName());
		return rate;
	}

}
