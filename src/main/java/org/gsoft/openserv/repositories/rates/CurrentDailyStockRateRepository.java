package org.gsoft.openserv.repositories.rates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.DailyStockRate;
import org.gsoft.openserv.domain.rates.Stock;
import org.springframework.stereotype.Repository;

@Repository
public class CurrentDailyStockRateRepository {
	@Resource
	private DailyStockRateRepository quoteRepository;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	public DailyStockRate findCurrentRateForStock(Stock stock) throws IOException, ParseException{
		 URL yahoofin = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + stock.getSymbol() + "&f=sd1oghl1&e=.csv");
         URLConnection yc = yahoofin.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
         String inputLine;
         if((inputLine = in.readLine()) != null) {
        	 String[] yahooStockInfo = inputLine.split(",");
             Date quoteDate = sdf.parse(yahooStockInfo[1].replace("\"", ""));
             DailyStockRate quote = quoteRepository.findDailyStockRate(stock, quoteDate);
             if(quote == null){
            	 quote = new DailyStockRate();
            	 quote.setStock(stock);
            	 quote.setQuoteDate(quoteDate);
            	 quoteRepository.save(quote);
             }
             quote.setOpenValue(new BigDecimal(yahooStockInfo[2]));
             quote.setLowValue(new BigDecimal(yahooStockInfo[3]));
             quote.setHighValue(new BigDecimal(yahooStockInfo[4]));
             quote.setLastValue(new BigDecimal(yahooStockInfo[5]));
             return quote;
        }
         return null;
	}
}
