package org.gsoft.phoenix.repositories.loan;

import org.gsoft.phoenix.domain.loan.LoanTransaction;
import org.gsoft.phoenix.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTransactionRepository extends BaseRepository<LoanTransaction, Long>{

}
