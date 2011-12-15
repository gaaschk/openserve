package org.gsoft.phoenix.repositories;

import java.util.List;

import org.gsoft.phoenix.domain.loan.Loan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends BaseRepository<Loan, Long> {
	@Query("select l from Loan l where l.borrowerPersonID = :borrowerPersonID")
	public List<Loan> findAllLoansByBorrowerPersonID(@Param("borrowerPersonID") Long borrowerPersonID);
}
