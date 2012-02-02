package org.gsoft.openserv.web.converters;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.DailyStockRate;
import org.gsoft.openserv.domain.rates.Stock;
import org.gsoft.openserv.repositories.rates.DailyStockRateRepository;
import org.gsoft.openserv.web.models.StockListModel;
import org.gsoft.openserv.web.models.StockModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StockListToStockListModelConverter implements Converter<List<Stock>, StockListModel>{
	@Resource
	private DailyStockRateRepository quoteRepository;
	
	@Override
	public StockListModel convert(List<Stock> source) {
		StockListModel stockList = new StockListModel();
		stockList.setStocks(new ArrayList<StockModel>());
		for(Stock stock:source){
			StockModel model = new StockModel();
			model.setStockID(stock.getStockID());
			model.setSymbol(stock.getSymbol());
			model.setName(stock.getName());
			DailyStockRate rate = quoteRepository.findMostRecentRate(stock);
			if(rate!=null){
				model.setQuoteDate(rate.getQuoteDate());
				model.setOpen(rate.getOpenValue());
				model.setHigh(rate.getHighValue());
				model.setLow(rate.getLowValue());
				model.setLast(rate.getLastValue());
			}
			stockList.getStocks().add(model);
		}
		return stockList;
	}

}
