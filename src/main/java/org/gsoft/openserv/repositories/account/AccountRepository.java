package org.gsoft.openserv.repositories.account;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Account;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository extends BaseRepository<Account, Long> {
	@Resource
	private AccountSpringRepository springRepository;
	
	@Override
	protected AccountSpringRepository getSpringRepository() {
		return springRepository;
	}

	public List<Account> findAccountsByBorrowerAndLoanProgramAndLenderID(Long borrowerPersonID, Long loanProgramID, Long lenderID){
		return this.getSpringRepository().findAccountsByBorrowerAndLoanProgramAndLenderIDAndNotInRepaymentBy(borrowerPersonID, loanProgramID, lenderID);
	}
}

interface AccountSpringRepository extends BaseSpringRepository<Account, Long>{
	@Query("SELECT account FROM Account account WHERE borrowerPersonID = :borrowerPersonID AND loanProgramID = :loanProgramID AND lenderID = :lenderID")
	List<Account> findAccountsByBorrowerAndLoanProgramAndLenderIDAndNotInRepaymentBy(@Param("borrowerPersonID") Long borrowerPersonID, @Param("loanProgramID") Long loanProgramID, @Param("lenderID") Long lenderID);
}
