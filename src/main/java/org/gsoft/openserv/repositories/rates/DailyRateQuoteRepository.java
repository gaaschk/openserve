package org.gsoft.openserv.repositories.rates;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.DailyRateQuote;
import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class DailyRateQuoteRepository extends BaseRepository<DailyRateQuote, Long>{
	@Resource
	private DailyRateQuoteSpringRepository dailyRateQuoteSpringRepository;

	@Override
	protected BaseSpringRepository<DailyRateQuote, Long> getSpringRepository() {
		return dailyRateQuoteSpringRepository;
	}

	public DailyRateQuote findDailyRateQuote(Rate rate, Date date){
		return this.dailyRateQuoteSpringRepository.findDailyRateQuote(rate, date);
	}

	public DailyRateQuote findDailyRateQuoteBySymbol(String symbol, Date date){
		return this.dailyRateQuoteSpringRepository.findDailyRateQuoteBySymbol(symbol, date);
	}

	public DailyRateQuote findMostRecentQuote(Rate rate){
		return this.dailyRateQuoteSpringRepository.findMostRecentQuote(rate);
	}
	
	public List<Object[]> findAllQuotesForDate(Date date){
		return this.dailyRateQuoteSpringRepository.findAllQuotesForDate(date);
	}
	
	public DailyRateQuote findCurrentRateAsOf(Rate rate, Date date){
		return this.dailyRateQuoteSpringRepository.findCurrentRateAsOf(rate, date);
	}
}

@Repository
interface DailyRateQuoteSpringRepository extends BaseSpringRepository<DailyRateQuote, Long>{
	
	@Query("select quote from DailyRateQuote quote where quote.rate = :rate and quote.quoteDate = :quoteDate")
	public DailyRateQuote findDailyRateQuote(@Param("rate") Rate rate, @Param("quoteDate") Date date);

	@Query("select quote from DailyRateQuote quote where quote.rate.symbol = :symbol and quote.quoteDate = :quoteDate")
	public DailyRateQuote findDailyRateQuoteBySymbol(@Param("symbol") String symbol, @Param("quoteDate") Date date);

	@Query("select quote from DailyRateQuote quote where quote.rate = :rate and quote.quoteDate = ( " +
			"select max(quoteDate) from DailyRateQuote quote2 where quote2.rate = :rate)")
	public DailyRateQuote findMostRecentQuote(@Param("rate") Rate rate);
	
	@Query("select rate,quote from DailyRateQuote quote right outer join quote.rate rate where quote is null or quote.quoteDate = :quoteDate")
	public List<Object[]> findAllQuotesForDate(@Param("quoteDate") Date date);
	
	@Query("select quote from DailyRateQuote quote where quote.rate = :rate and quote.quoteDate = ( " +
			"select max(q2.quoteDate) from DailyRateQuote q2 where q2.rate = :rate and q2.quoteDate <= :date)")
	public DailyRateQuote findCurrentRateAsOf(@Param("rate") Rate rate, @Param("date")Date date);
}
