package org.gsoft.openserv.repositories.rates;

import java.util.List;

import org.gsoft.openserv.domain.rates.Stock;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends BaseRepository<Stock, Long>{
	
	@Query("select stock from Stock stock where stock.autoUpdate = true")
	public List<Stock> findAllForUpdate();
}
