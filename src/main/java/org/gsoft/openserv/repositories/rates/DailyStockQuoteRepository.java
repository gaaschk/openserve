package org.gsoft.openserv.repositories.rates;

import java.util.Date;

import org.gsoft.openserv.domain.rates.DailyStockQuote;
import org.gsoft.openserv.domain.rates.Stock;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStockQuoteRepository extends BaseRepository<DailyStockQuote, Long>{
	
	@Query("select quote from DailyStockQuote quote where quote.stock = :stock and quote.quoteDate = :quoteDate")
	public DailyStockQuote findDailyStockQuote(@Param("stock") Stock stock, @Param("quoteDate") Date date);

	@Query("select quote from DailyStockQuote quote where quote.stock = :stock and quote.quoteDate = ( " +
			"select max(quoteDate) from DailyStockQuote quote2 where quote2.stock = :stock)")
	public DailyStockQuote findMostRecentQuote(@Param("stock") Stock stock);
}
