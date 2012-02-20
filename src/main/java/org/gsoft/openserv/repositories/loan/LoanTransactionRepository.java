package org.gsoft.openserv.repositories.loan;

import org.gsoft.openserv.domain.loan.LoanTransaction;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTransactionRepository extends BaseSpringRepository<LoanTransaction, Long>{

}
