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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.domain.rates.Rate;
import org.springframework.stereotype.Repository;

@Repository
public class CurrentRateValueRepository {
	private static final Logger LOG = LogManager.getLogger(CurrentRateValueRepository.class);
	
	@Resource
	private RateValueRepository quoteRepository;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	public RateValue findCurrentValueForRate(Rate rate) throws IOException, ParseException{
		 URL yahoofin = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + rate.getTickerSymbol() + "&f=sd1oghl1&e=.csv");
         URLConnection yc = yahoofin.openConnection();
         String inputLine = null;
         BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
         try{
        	 inputLine = in.readLine();
         }
         finally{
        	 in.close();
         }
         if(inputLine != null) {
        	 String[] yahooStockInfo = inputLine.split(",");
             Date quoteDate = sdf.parse(yahooStockInfo[1].replace("\"", ""));
             RateValue rateValue = quoteRepository.findRateValue(rate, quoteDate);
             if(rateValue == null){
            	 rateValue = new RateValue();
            	 rateValue.setRate(rate);
            	 rateValue.setRateValueDate(quoteDate);
            	 quoteRepository.save(rateValue);
             }
             try{
            	 rateValue.setRateValue(new BigDecimal(yahooStockInfo[5]));
            	 return rateValue;
             }
             catch(NumberFormatException nfe){
            	 LOG.warn("Unable to parse quote. [" + yahooStockInfo + "]");
             }
        }
        return null;
	}
}
