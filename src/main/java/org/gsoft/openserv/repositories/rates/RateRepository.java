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

	public Rate findRateBySymbol(String symbol){
		return this.rateSpringRepository.findRateBySymbol(symbol);
	}
}

@Repository
interface RateSpringRepository extends BaseSpringRepository<Rate, Long>{
	
	@Query("select rate from Rate rate where rate.symbol = :symbol")
	public Rate findRateBySymbol(@Param("symbol") String symbol);
}
