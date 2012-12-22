package org.gsoft.openserv.web.converters.model;

import java.util.ArrayList;
import java.util.List;

import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.web.models.RateListModel;
import org.gsoft.openserv.web.models.RateModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RateValueListToRateListModelConverter implements Converter<List<RateValue>, RateListModel>{
	@Override
	public RateListModel convert(List<RateValue> source) {
		RateListModel rateList = new RateListModel();
		rateList.setRates(new ArrayList<RateModel>());
		for(RateValue rate:source){
			RateModel model = new RateModel();
			model.setRateID(rate.getRate().getRateId());
			model.setSymbol(rate.getRate().getTickerSymbol());
			model.setName(rate.getRate().getRateName());
			model.setQuoteDate(rate.getRateValueDate());
			model.setValue(rate.getRateValue());
			rateList.getRates().add(model);
		}
		return rateList;
	}

}
