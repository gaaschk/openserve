package org.gsoft.openserv.repositories.rates;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class RateRepository extends BaseRepository<Rate, Long>{
	@Resource
	private RateSpringRepository rateSpringRepository;

	@Override
	protected BaseSpringRepository<Rate, Long> getSpringRepository() {
		return rateSpringRepository;
	}

	public Rate findRateByTickerSymbol(String tickerSymbol){
		return this.rateSpringRepository.findRateByTickerSymbol(tickerSymbol);
	}
}

@Repository
interface RateSpringRepository extends BaseSpringRepository<Rate, Long>{
	
	@Query("select rate from Rate rate where rate.tickerSymbol = :tickerSymbol")
	Rate findRateByTickerSymbol(@Param("tickerSymbol") String tickerSymbol);
}
