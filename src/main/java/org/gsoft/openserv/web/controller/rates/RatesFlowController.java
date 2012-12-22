package org.gsoft.openserv.web.controller.rates;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.gsoft.openserv.repositories.rates.RateValueRepository;
import org.gsoft.openserv.service.rates.RateService;
import org.gsoft.openserv.web.models.RateListModel;
import org.gsoft.openserv.web.models.RateModel;
import org.joda.time.DateTime;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class RatesFlowController {
	@Resource
	private RateValueRepository rateValueRepository;
	@Resource
	private RateRepository rateRepository;
	@Resource
	private ConversionService conversionService;
	@Resource
	private SystemSettingsLogic systemSettings;
	@Resource
	private RateService rateService;
	
	public RateListModel findAllRateValueForCurrentDate(){
		return this.findAllRateValuesForDate(systemSettings.getCurrentSystemDate());
	}
	
	public RateListModel findAllRateValuesForDate(Date day){
		Date dayNoTime = new DateTime(day).toDateMidnight().toDate();
		List<Rate> rates = rateService.getAllRates();
		ArrayList<RateValue> rateValues = new ArrayList<RateValue>();
		for(Rate rate:rates){
			RateValue rateValue = null;
			rateValue = rateValueRepository.findRateValue(rate, dayNoTime);
			if(rateValue == null){
				rateValue = new RateValue();
				rateValue.setRate(rate);
				rateValues.add(rateValue);
			}
			else{
				rateValues.add(rateValue);
			}
			rateValue.setRateValueDate(dayNoTime);
		}
		RateListModel ratesModel = conversionService.convert(rateValues, RateListModel.class);
		ratesModel.setQuoteDate(dayNoTime);
		return ratesModel;
	}
	
	public RateListModel addRate(RateListModel rates){
		Rate newRate = new Rate();
		newRate.setTickerSymbol(rates.getNewRateSymbol());
		newRate.setRateName(rates.getNewRateName());
		rateRepository.save(newRate);
		return this.findAllRateValuesForDate(rates.getQuoteDate());
	}
	
	public RateListModel saveRates(RateListModel rateList){
		List<RateModel> rateModels = rateList.getRates();
		for(RateModel rateModel:rateModels){
			rateModel.setQuoteDate(rateList.getQuoteDate());
			RateValue rateValue = conversionService.convert(rateModel, RateValue.class);
			rateValue.getRate().setRateName(rateModel.getName());
			rateService.saveRateValue(rateValue);
		}
		return this.findAllRateValuesForDate(rateList.getQuoteDate());
	}
}
