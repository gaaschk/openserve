package org.gsoft.openserv.service.rates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.rates.QuoteUpdater;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.gsoft.openserv.repositories.rates.RateValueRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class RateService {
	@Resource
	private QuoteUpdater quoteUpdater;
	@Resource
	private RateRepository rateRepository;
	@Resource
	private RateValueRepository rateValueRepository;
	
	@Transactional
//	@Scheduled(fixedRate=30000)
	public void updateAllQuotes(){
		quoteUpdater.updateQuotes();
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
	public void saveRateValue(RateValue rate){
		rateValueRepository.save(rate);
	}
}
