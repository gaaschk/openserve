package org.gsoft.openserv.repositories.loan;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends BaseRepository<Loan, Long>{
}
