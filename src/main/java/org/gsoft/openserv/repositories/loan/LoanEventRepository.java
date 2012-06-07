package org.gsoft.openserv.repositories.loan;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanEvent;
import org.gsoft.openserv.domain.loan.LoanEventType;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.gsoft.openserv.repositories.predicates.LoanEventPredicates;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class LoanEventRepository extends BaseRepository<LoanEvent, Long>{
	@Resource
	private LoanEventRepoIF loanEventRepoIF;

	@Override
	protected BaseSpringRepository<LoanEvent, Long> getSpringRepository() {
		return this.loanEventRepoIF;
	}
	
	public LoanEvent findMostRecentLoanEventWithTransactionEffectiveOnOrBeforeDate(Long loanID, Date checkDate){
		List<LoanEvent> result = this.loanEventRepoIF.findAll(LoanEventPredicates.loanIDIsAndEffectiveDateLessThanOrEqualToAndHasTransaction(loanID, checkDate), new PageRequest(0,1,Direction.DESC,"sequence")).getContent();
		return (result == null || result.size() <= 0)?null:result.get(0);
	}

	public LoanEvent findMostRecentLoanEventWithTransactionPriorToSequence(Long loanID, Integer sequence){
		return this.loanEventRepoIF.findMostRecentLoanEventWithTransactionPriorToSequence(loanID, sequence);
	}

	public LoanEvent findMostRecentLoanEventWithTransaction(Long loanID){
		return this.loanEventRepoIF.findMostRecentLoanEventWithTransaction(loanID);
	}

	public List<LoanEvent> findAllByLoanID(Long loanID){
		return this.loanEventRepoIF.findAllByLoanID(loanID);
	}
	
	public List<LoanEvent> findAllLoanEventsAfterDate(Long loanID, Date fromDate){
		return this.loanEventRepoIF.findAllLoanEventsAfterDate(loanID, fromDate);
	}
	
	public List<LoanEvent> findAllLoanEventsOfTypeForLoan(Long loanID, LoanEventType loanEventType){
		return this.loanEventRepoIF.findAllLoanEventsOfTypeForLoan(loanID, loanEventType);
	}

	public List<LoanEvent> findAllLoanEventsForLoanAfterSequence(Long loanID, Integer sequence){
		return this.loanEventRepoIF.findAllLoanEventsForLoanAfterSequence(loanID, sequence);
	}
}

@Repository
interface LoanEventRepoIF extends BaseSpringRepository<LoanEvent, Long>{
	
	@Query("select le from LoanEvent le where le.loanID = :loanID and le.sequence = " +
			"(select max(le2.sequence) from LoanEvent le2 join le2.loanTransaction where le2.loanID = :loanID and le2.effectiveDate <= :checkDate)")
	public LoanEvent findMostRecentLoanEventWithTransactionEffectiveOnOrBeforeDate(@Param("loanID") Long loanID, @Param("checkDate") Date checkDate);

	@Query("select le from LoanEvent le where le.loanTransaction is not null and le.loanID = :loanID and le.sequence = " +
			"(select max(le2.sequence) from LoanEvent le2 where le2.loanID = :loanID and le2.sequence < :sequence)")
	public LoanEvent findMostRecentLoanEventWithTransactionPriorToSequence(@Param("loanID") Long loanID, @Param("sequence") Integer sequence);

	@Query("select le from LoanEvent le where le.loanTransaction is not null and le.loanID = :loanID and le.sequence = " +
			"(select max(le2.sequence) from LoanEvent le2 where le2.loanID = :loanID)")
	public LoanEvent findMostRecentLoanEventWithTransaction(@Param("loanID") Long loanID);

	@Query("select le from LoanEvent le where le.loanID = :loanID order by le.effectiveDate desc, le.sequence desc")
	public List<LoanEvent> findAllByLoanID(@Param("loanID") Long loanID);
	
	@Query("select le from LoanEvent le where le.loanID = :loanID and le.effectiveDate > :fromDate order by le.effectiveDate desc, le.sequence desc")
	public List<LoanEvent> findAllLoanEventsAfterDate(@Param("loanID") Long loanID, @Param("fromDate") Date fromDate);
	
	@Query("select le from LoanEvent le where le.loanID = :loanID and le.loanEventType = :loanEventType order by le.effectiveDate desc, le.sequence desc")
	public List<LoanEvent> findAllLoanEventsOfTypeForLoan(@Param("loanID") Long loanID, @Param("loanEventType") LoanEventType loanEventType);

	@Query("select le from LoanEvent le where le.loanID = :loanID and le.sequence > :sequence order by le.sequence desc")
	public List<LoanEvent> findAllLoanEventsForLoanAfterSequence(@Param("loanID") Long loanID, @Param("sequence") Integer sequence);
}
