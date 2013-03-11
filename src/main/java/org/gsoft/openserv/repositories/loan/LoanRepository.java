package org.gsoft.openserv.repositories.loan;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.gsoft.openserv.repositories.predicates.LoanPredicates;
import org.springframework.stereotype.Repository;

@Repository
public class LoanRepository extends BaseRepository<Loan, Long>{
	@Resource
	private LoanRepoIF loanRepo;
	
	protected LoanRepoIF getSpringRepository(){
		return loanRepo;
	}
	
	public List<Loan> findAllForBorrowerActiveOnOrBefore(Long borrowerID, Date activeDate){
		return this.findAll(LoanPredicates.borrowerIdIsAndActiveOnOrBefore(borrowerID, activeDate));
	}
	
	public List<Loan> findAllForBorrower(Long borrowerID){
		return this.findAll(LoanPredicates.borrowerIdIs(borrowerID));
	}
	
	public List<Loan> findAllByLoanProgram(LoanProgram loanProgram){
		return this.findAll(LoanPredicates.loanProgramIs(loanProgram));
	}
	
	public List<Loan> findAllByAccountID(Long accountID){
		return this.findAll(LoanPredicates.accountIDIs(accountID));
	}
}

@Repository
interface LoanRepoIF extends BaseSpringRepository<Loan, Long>{} 
