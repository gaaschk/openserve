package org.gsoft.openserv.web.controller.rates;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.Stock;
import org.gsoft.openserv.service.rates.QuoteService;
import org.gsoft.openserv.web.models.StockListModel;
import org.gsoft.openserv.web.models.StockModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("rates")
public class StockController {
	@Resource
	private QuoteService quoteService;
	@Resource
	private ConversionService conversionService;
	
	@RequestMapping(value="/stocks", method=RequestMethod.GET)
	public ModelAndView loadCurrentRates(ModelAndView model){
		List<Stock> stocks = quoteService.getAllStocks();
		StockListModel stockList = conversionService.convert(stocks, StockListModel.class);
		model.addObject("stockList", stockList);
		model.setViewName("rates/stocklist");
		return model;
	}

	@RequestMapping(value="/stocks", method=RequestMethod.POST)
	public ModelAndView saveStock(@ModelAttribute("stockList") StockListModel stockListModel, ModelAndView model){
		StockModel stockModel = stockListModel.getEditingStock();
		conversionService.convert(stockModel, Stock.class);
		return this.loadCurrentRates(model);
	}
}
