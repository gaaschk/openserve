package org.gsoft.phoenix.repositories;

import java.util.List;

import org.gsoft.phoenix.domain.Loan;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends BaseRepository<Loan, Long> {
	public List<Loan> findAllLoansByBorrowerPersonID(Long borrowerPersonID);
}
