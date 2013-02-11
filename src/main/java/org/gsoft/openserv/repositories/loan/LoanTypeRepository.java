package org.gsoft.openserv.repositories.loan;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanType;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LoanTypeRepository extends BaseRepository<LoanType, Long>{
	@Resource
	private LoanTypeSpringRepository loanTypeSpringRepository;

	@Override
	protected LoanTypeSpringRepository getSpringRepository() {
		return this.loanTypeSpringRepository;
	}
}

@Repository
interface LoanTypeSpringRepository extends BaseSpringRepository<LoanType, Long>{

}
