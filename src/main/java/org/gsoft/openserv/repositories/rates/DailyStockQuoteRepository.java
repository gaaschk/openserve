package org.gsoft.openserv.repositories.rates;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.DailyStockQuote;
import org.gsoft.openserv.domain.rates.Stock;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class DailyStockQuoteRepository extends BaseRepository<DailyStockQuote, Long>{
	@Resource
	private DailyStockQuoteSpringRepository dailyStockQuoteSpringRepository;

	@Override
	protected BaseSpringRepository<DailyStockQuote, Long> getSpringRepository() {
		return dailyStockQuoteSpringRepository;
	}
	
	public DailyStockQuote findDailyStockQuote(Stock stock, Date date){
		return this.dailyStockQuoteSpringRepository.findDailyStockQuote(stock, date);
	}

	public DailyStockQuote findMostRecentQuote(@Param("stock") Stock stock){
		return this.dailyStockQuoteSpringRepository.findMostRecentQuote(stock);
	}
}

@Repository
interface DailyStockQuoteSpringRepository extends BaseSpringRepository<DailyStockQuote, Long>{
	
	@Query("select quote from DailyStockQuote quote where quote.stock = :stock and quote.quoteDate = :quoteDate")
	public DailyStockQuote findDailyStockQuote(@Param("stock") Stock stock, @Param("quoteDate") Date date);

	@Query("select quote from DailyStockQuote quote where quote.stock = :stock and quote.quoteDate = ( " +
			"select max(quoteDate) from DailyStockQuote quote2 where quote2.stock = :stock)")
	public DailyStockQuote findMostRecentQuote(@Param("stock") Stock stock);
}
