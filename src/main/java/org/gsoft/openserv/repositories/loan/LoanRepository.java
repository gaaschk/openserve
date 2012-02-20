package org.gsoft.openserv.repositories.loan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.predicates.LoanPredicates;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.gsoft.openserv.util.ListUtility;
import org.springframework.stereotype.Repository;

@Repository
public class LoanRepository {
	@Resource
	private LoanRepoIF loanRepo;
	
	public List<Loan> findAllForBorrowerActiveOnOrBefore(Long borrowerID, Date activeDate){
		return ListUtility.addAll(new ArrayList<Loan>(), loanRepo.findAll(LoanPredicates.borrowerIdIsAndActiveOnOrBefore(borrowerID, activeDate)));
	}
	
	public List<Loan> findAllForBorrower(Long borrowerID){
		return ListUtility.addAll(new ArrayList<Loan>(), this.loanRepo.findAll(LoanPredicates.borrowerIdIs(borrowerID)));
	}
	
	public Loan findOne(Long loanId){
		return this.loanRepo.findOne(loanId);
	}
	
	public List<Loan> findAll(){
		return ListUtility.addAll(new ArrayList<Loan>(), this.loanRepo.findAll());
	}
	
	public Loan save(Loan loan){
		return this.loanRepo.save(loan);
	}
}

@Repository
interface LoanRepoIF extends BaseSpringRepository<Loan, Long>{} 
