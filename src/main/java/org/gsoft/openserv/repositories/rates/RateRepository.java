package org.gsoft.openserv.repositories.rates;

import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends BaseRepository<Rate, Long>{
	
	@Query("select rate from Rate rate where rate.symbol = :symbol")
	public Rate findRateBySymbol(@Param("symbol") String symbol);
}
