package org.gsoft.openserv.web.controller.rates;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.service.rates.RateService;
import org.gsoft.openserv.web.models.RateListModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("rates")
public class RateController {
	@Resource
	private RateService rateService;
	@Resource
	private ConversionService conversionService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView loadCurrentRates(ModelAndView model){
		List<Rate> rates = rateService.getAllRates();
		RateListModel rateList = conversionService.convert(rates, RateListModel.class);
		model.addObject("rateList", rateList);
		model.setViewName("rates/list");
		return model;
	}

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView saveRate(@ModelAttribute("list") RateListModel rateListModel, ModelAndView model){
//		StockModel stockModel = rateListModel.getEditingStock();
//		conversionService.convert(stockModel, Rate.class);
		return this.loadCurrentRates(model);
	}
}
