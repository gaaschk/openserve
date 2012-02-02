package org.gsoft.openserv.repositories.rates;

import java.util.Date;

import org.gsoft.openserv.domain.rates.DailyStockRate;
import org.gsoft.openserv.domain.rates.Stock;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStockRateRepository extends BaseRepository<DailyStockRate, Long>{
	
	@Query("select quote from DailyStockRate quote where quote.stock = :stock and quote.quoteDate = :quoteDate")
	public DailyStockRate findDailyStockRate(@Param("stock") Stock stock, @Param("quoteDate") Date date);

	@Query("select quote from DailyStockRate quote where quote.stock = :stock and quote.quoteDate = ( " +
			"select max(quoteDate) from DailyStockRate quote2 where quote2.stock = :stock)")
	public DailyStockRate findMostRecentRate(@Param("stock") Stock stock);
}
