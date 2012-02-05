package org.gsoft.openserv.service.rates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.rates.QuoteUpdater;
import org.gsoft.openserv.domain.rates.DailyRateQuote;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.domain.rates.Stock;
import org.gsoft.openserv.repositories.rates.DailyRateQuoteRepository;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.gsoft.openserv.repositories.rates.StockRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class QuoteService {
	@Resource
	private QuoteUpdater quoteUpdater;
	@Resource
	private StockRepository stockRepository;
	@Resource
	private RateRepository rateRepository;
	@Resource
	private DailyRateQuoteRepository dailyRateQuoteRepository;
	
	@Transactional
	@Scheduled(fixedRate=30000)
	public void updateAllQuotes(){
		quoteUpdater.updateQuotes();
	}
	
	public List<Stock> getAllStocks(){
		Iterable<Stock> stocks = stockRepository.findAll();
		Iterator<Stock> stockIter = stocks.iterator();
		ArrayList<Stock> stockList = new ArrayList<Stock>();
		while(stockIter.hasNext()){
			stockList.add(stockIter.next());
		}
		return stockList;
	}
	
	public List<Rate> getAllRates(){
		Iterable<Rate> rates = rateRepository.findAll();
		Iterator<Rate> rateIter = rates.iterator();
		ArrayList<Rate> rateList = new ArrayList<Rate>();
		while(rateIter.hasNext()){
			rateList.add(rateIter.next());
		}
		return rateList;
	}

	@Transactional
	public void saveRate(DailyRateQuote quote){
		dailyRateQuoteRepository.save(quote);
	}
}
