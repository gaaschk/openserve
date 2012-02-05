package org.gsoft.openserv.web.converters.model;

import java.util.ArrayList;
import java.util.List;

import org.gsoft.openserv.domain.rates.DailyRateQuote;
import org.gsoft.openserv.web.models.RateListModel;
import org.gsoft.openserv.web.models.RateModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RateListToRateListModelConverter implements Converter<List<DailyRateQuote>, RateListModel>{
	@Override
	public RateListModel convert(List<DailyRateQuote> source) {
		RateListModel rateList = new RateListModel();
		rateList.setRates(new ArrayList<RateModel>());
		for(DailyRateQuote rate:source){
			RateModel model = new RateModel();
			model.setRateID(rate.getRate().getRateID());
			model.setSymbol(rate.getRate().getSymbol());
			model.setName(rate.getRate().getName());
			model.setQuoteDate(rate.getQuoteDate());
			model.setValue(rate.getValue());
			rateList.getRates().add(model);
		}
		return rateList;
	}

}
