package org.gsoft.openserv.repositories.loan;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends BaseRepository<Loan, Long> {
	@Query("select l from Loan l where l.borrower.personID = :borrowerPersonID and l.servicingStartDate <= :activeDate")
	public List<Loan> findAllLoansByBorrowerPersonIDActiveOnOrBeforeDate(@Param("borrowerPersonID") Long borrowerPersonID, @Param("activeDate") Date activeDate);
	@Query("select l from Loan l where l.borrower.personID = :borrowerPersonID")
	public List<Loan> findAllLoansByBorrowerPersonID(@Param("borrowerPersonID") Long borrowerPersonID);
}
