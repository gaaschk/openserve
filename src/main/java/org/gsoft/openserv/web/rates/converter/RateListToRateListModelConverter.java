package org.gsoft.openserv.web.rates.converter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.repositories.rates.RateValueRepository;
import org.gsoft.openserv.web.rates.model.RateListModel;
import org.gsoft.openserv.web.rates.model.RateModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RateListToRateListModelConverter implements Converter<List<Rate>, RateListModel>{
	@Resource
	private RateValueRepository rateValueRepository;
	
	@Override
	public RateListModel convert(List<Rate> source) {
		RateListModel rateList = new RateListModel();
		rateList.setRates(new ArrayList<RateModel>());
		for(Rate rate:source){
			RateModel model = new RateModel();
			model.setRateID(rate.getRateId());
			model.setSymbol(rate.getTickerSymbol());
			model.setName(rate.getRateName());
			RateValue rateValue = rateValueRepository.findMostRecentQuote(rate);
			if(rate!=null){
				model.setQuoteDate(rateValue.getRateValueDate());
				model.setValue(rateValue.getRateValue());
			}
			rateList.getRates().add(model);
		}
		return rateList;
	}

}
