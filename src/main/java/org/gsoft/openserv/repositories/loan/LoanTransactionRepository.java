package org.gsoft.openserv.repositories.loan;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanTransaction;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

public class LoanTransactionRepository extends BaseRepository<LoanTransaction, Long>{
	@Resource
	private LoanTransactionSpringRepository loanTransactionSpringRepository;

	@Override
	protected BaseSpringRepository<LoanTransaction, Long> getSpringRepository() {
		return this.loanTransactionSpringRepository;
	}
	
}

@Repository
interface LoanTransactionSpringRepository extends BaseSpringRepository<LoanTransaction, Long>{

}
