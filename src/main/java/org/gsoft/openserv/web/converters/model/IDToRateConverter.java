package org.gsoft.openserv.web.converters.model;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IDToRateConverter  implements Converter<String, Rate>{
	@Resource
	private RateRepository rateRepository;
	
	public Rate convert(String id){
		return rateRepository.findOne(Long.valueOf(id));
	}
}
