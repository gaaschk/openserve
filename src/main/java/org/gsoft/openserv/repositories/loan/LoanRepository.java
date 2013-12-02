package org.gsoft.openserv.repositories.loan;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends BaseRepository<Loan, Long>{
	@Query("select loan from Loan loan where loan.borrower.personID = :borrowerID and loan.servicingStartDate <= :activeDate")
	public List<Loan> findAllForBorrowerActiveOnOrBefore(@Param("borrowerID") Long borrowerID, @Param("activeDate") Date activeDate);
	
	@Query("select loan from Loan loan where loan.borrower.personID = :borrowerID")
	public List<Loan> findAllForBorrower(@Param("borrowerID") Long borrowerID);
	
	@Query("select loan from Loan loan where loan.loanProgram.loanProgramID = :loanProgramID")
	public List<Loan> findAllByLoanProgram(@Param("loanProgramID") Long loanProgramID);
	
	@Query("select loan from Loan loan where loan.account.accountID = :accountID")
	public List<Loan> findAllByAccountID(@Param("accountID") Long accountID);
}