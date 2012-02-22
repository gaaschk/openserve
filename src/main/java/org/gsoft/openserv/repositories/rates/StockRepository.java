package org.gsoft.openserv.repositories.rates;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.rates.Stock;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepository extends BaseRepository<Stock, Long>{
	@Resource
	private StockSpringRepository stockSpringRepository;

	@Override
	protected BaseSpringRepository<Stock, Long> getSpringRepository() {
		return stockSpringRepository;
	}

	public List<Stock> findAllForUpdate(){
		return this.stockSpringRepository.findAllForUpdate();
	}
}

@Repository
interface StockSpringRepository extends BaseSpringRepository<Stock, Long>{
	
	@Query("select stock from Stock stock where stock.autoUpdate = true")
	public List<Stock> findAllForUpdate();
}
