package org.gsoft.openserv.web.controller.rates;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.rates.DailyRateQuote;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.repositories.rates.DailyRateQuoteRepository;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.gsoft.openserv.service.rates.QuoteService;
import org.gsoft.openserv.web.models.RateListModel;
import org.gsoft.openserv.web.models.RateModel;
import org.joda.time.DateTime;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class RatesFlowController {
	@Resource
	private DailyRateQuoteRepository dailyRateQuoteRepository;
	@Resource
	private RateRepository rateRepository;
	@Resource
	private ConversionService conversionService;
	@Resource
	private SystemSettingsLogic systemSettings;
	@Resource
	private QuoteService quoteService;
	
	public RateListModel findAllQuotesForCurrentDate(){
		return this.findAllQuotesForDate(systemSettings.getCurrentSystemDate());
	}
	
	public RateListModel findAllQuotesForDate(Date day){
		Date dayNoTime = new DateTime(day).toDateMidnight().toDate();
		List<Rate> rates = quoteService.getAllRates();
		ArrayList<DailyRateQuote> quotes = new ArrayList<DailyRateQuote>();
		for(Rate rate:rates){
			DailyRateQuote quote = null;
			quote = dailyRateQuoteRepository.findDailyRateQuote(rate, dayNoTime);
			if(quote == null){
				quote = new DailyRateQuote();
				quote.setRate(rate);
				quotes.add(quote);
			}
			else{
				quotes.add(quote);
			}
			quote.setQuoteDate(dayNoTime);
		}
		RateListModel ratesModel = conversionService.convert(quotes, RateListModel.class);
		ratesModel.setQuoteDate(dayNoTime);
		return ratesModel;
	}
	
	public RateListModel addRate(RateListModel rates){
		Rate newRate = new Rate();
		newRate.setSymbol(rates.getNewRateSymbol());
		newRate.setName(rates.getNewRateName());
		rateRepository.save(newRate);
		return this.findAllQuotesForDate(rates.getQuoteDate());
	}
	
	public RateListModel saveRates(RateListModel rateList){
		List<RateModel> rateModels = rateList.getRates();
		for(RateModel rateModel:rateModels){
			rateModel.setQuoteDate(rateList.getQuoteDate());
			DailyRateQuote quote = conversionService.convert(rateModel, DailyRateQuote.class);
			quote.getRate().setName(rateModel.getName());
			quoteService.saveRate(quote);
		}
		return this.findAllQuotesForDate(rateList.getQuoteDate());
	}
}
