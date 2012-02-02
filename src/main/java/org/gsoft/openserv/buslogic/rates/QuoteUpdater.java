package org.gsoft.openserv.buslogic.rates;

import java.util.Iterator;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.Stock;
import org.gsoft.openserv.repositories.rates.CurrentDailyStockRateRepository;
import org.gsoft.openserv.repositories.rates.StockRepository;
import org.springframework.stereotype.Component;

@Component
public class QuoteUpdater {
	@Resource
	private StockRepository stockRepository;
	@Resource
	private CurrentDailyStockRateRepository quoteFinder;
	
	public void updateQuotes(){
		Iterable<Stock> stocks = stockRepository.findAll();
		Iterator<Stock> stockIter = stocks.iterator();
		while(stockIter.hasNext()){
			try {
				quoteFinder.findCurrentRateForStock(stockIter.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
