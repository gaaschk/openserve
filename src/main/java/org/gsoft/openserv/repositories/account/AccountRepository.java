package org.gsoft.openserv.repositories.account;

import java.util.List;

import org.gsoft.openserv.domain.loan.Account;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long>{
	@Query("SELECT account FROM Account account WHERE borrowerPersonID = :borrowerPersonID AND loanProgramID = :loanProgramID AND lenderID = :lenderID")
	List<Account> findAccountsByBorrowerAndLoanProgramAndLenderIDAndNotInRepaymentBy(@Param("borrowerPersonID") Long borrowerPersonID, @Param("loanProgramID") Long loanProgramID, @Param("lenderID") Long lenderID);
}