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

	public List<Account> findAccountsByBorrowerAndLoanTypeAndLenderID(Long borrowerPersonID, Long loanTypeID, Long lenderID){
		return this.getSpringRepository().findAccountsByBorrowerAndLoanTypeAndLenderIDAndNotInRepaymentBy(borrowerPersonID, loanTypeID, lenderID);
	}
}

interface AccountSpringRepository extends BaseSpringRepository<Account, Long>{
	@Query("SELECT account FROM Account account WHERE borrowerPersonID = :borrowerPersonID AND loanTypeID = :loanTypeID AND lenderID = :lenderID")
	public List<Account> findAccountsByBorrowerAndLoanTypeAndLenderIDAndNotInRepaymentBy(@Param("borrowerPersonID") Long borrowerPersonID, @Param("loanTypeID") Long loanTypeID, @Param("lenderID") Long lenderID);
}
