package org.gsoft.openserv.repositories.loan;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LoanProgramRepository extends BaseRepository<LoanProgram, Long>{
	@Resource
	private LoanProgramSpringRepository loanProgramSpringRepository;

	@Override
	protected LoanProgramSpringRepository getSpringRepository() {
		return this.loanProgramSpringRepository;
	}
}

@Repository
interface LoanProgramSpringRepository extends BaseSpringRepository<LoanProgram, Long>{

}
