package org.gsoft.openserv.repositories.rates;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.domain.rates.RateValue;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RateValueRepository extends BaseRepository<RateValue, Long>{
	
	@Query("select rateValue from RateValue rateValue where rateValue.rate = :rate and rateValue.rateValueDate = (select max(rateValue.rateValueDate) from RateValue rateValue where rateValue.rateValueDate <= :rateValueDate)")
	public RateValue findRateValue(@Param("rate") Rate rate, @Param("rateValueDate") Date date);

	@Query("select rateValue from RateValue rateValue where rateValue.rate.tickerSymbol = :tickerSymbol and rateValue.rateValueDate <= :rateValueDate")
	public RateValue findRateValueByTickerSymbol(@Param("tickerSymbol") String tickerSymbol, @Param("rateValueDate") Date date);

	@Query("select rateValue from RateValue rateValue where rateValue.rate = :rate and rateValue.rateValueDate = ( " +
			"select max(rateValueDate) from RateValue rateValue2 where rateValue2.rate = :rate)")
	public RateValue findMostRecentQuote(@Param("rate") Rate rate);
	
	@Query("select rate,rateValue from RateValue rateValue right outer join rateValue.rate rate where rateValue is null or rateValue.rateValueDate = :rateValueDate")
	public List<Object[]> findAllQuotesForDate(@Param("rateValueDate") Date date);
	
	@Query("select rateValue from RateValue rateValue where rateValue.rate = :rate and rateValue.rateValueDate = ( " +
			"select max(q2.rateValueDate) from RateValue q2 where q2.rate = :rate and q2.rateValueDate <= :date)")
	public RateValue findCurrentRateAsOf(@Param("rate") Rate rate, @Param("date")Date date);
}