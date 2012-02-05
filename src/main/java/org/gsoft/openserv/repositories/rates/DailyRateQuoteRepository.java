package org.gsoft.openserv.repositories.rates;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.rates.DailyRateQuote;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyRateQuoteRepository extends BaseRepository<DailyRateQuote, Long>{
	
	@Query("select quote from DailyRateQuote quote where quote.rate = :rate and quote.quoteDate = :quoteDate")
	public DailyRateQuote findDailyRateQuote(@Param("rate") Rate rate, @Param("quoteDate") Date date);

	@Query("select quote from DailyRateQuote quote where quote.rate.symbol = :symbol and quote.quoteDate = :quoteDate")
	public DailyRateQuote findDailyRateQuoteBySymbol(@Param("symbol") String symbol, @Param("quoteDate") Date date);

	@Query("select quote from DailyRateQuote quote where quote.rate = :rate and quote.quoteDate = ( " +
			"select max(quoteDate) from DailyRateQuote quote2 where quote2.rate = :rate)")
	public DailyRateQuote findMostRecentQuote(@Param("rate") Rate rate);
	
	@Query("select rate,quote from DailyRateQuote quote right outer join quote.rate rate where quote is null or quote.quoteDate = :quoteDate")
	public List<Object[]> findAllQuotesForDate(@Param("quoteDate") Date date);
}
