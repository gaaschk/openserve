package org.gsoft.openserv.buslogic.rates;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.repositories.rates.CurrentRateValueRepository;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.springframework.stereotype.Component;

@Component
public class QuoteUpdater {
	@Resource
	private RateRepository rateRepository;
	@Resource
	private CurrentRateValueRepository rateFinder;
	
	public void updateQuotes(){
		Iterable<Rate> rates = rateRepository.findAll();
		Iterator<Rate> rateIter = rates.iterator();
		while(rateIter.hasNext()){
			try {
				rateFinder.findCurrentValueForRate(rateIter.next());
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
