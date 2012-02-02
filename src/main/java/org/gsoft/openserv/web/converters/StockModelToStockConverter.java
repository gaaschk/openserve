package org.gsoft.openserv.web.converters;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.Stock;
import org.gsoft.openserv.repositories.rates.StockRepository;
import org.gsoft.openserv.web.models.StockModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StockModelToStockConverter implements Converter<StockModel, Stock>{
	@Resource
	private StockRepository stockRepository;

	@Override
	public Stock convert(StockModel source) {
		Stock stock = null;
		if(source.getStockID() != null){
			stock = stockRepository.findOne(source.getStockID());
		}
		else{
			stock = new Stock();
			stockRepository.save(stock);
		}
		stock.setSymbol(source.getSymbol());
		stock.setName(source.getName());
		return stock;
	}

}
