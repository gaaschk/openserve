package org.gsoft.openserv.repositories.loan;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanType;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class LoanTypeProfileRepository extends BaseRepository<LoanTypeProfile, Long>{
	@Resource
	private LoanTypeProfileSpringRepository loanTypeProfileSpringRepository;

	@Override
	protected LoanTypeProfileSpringRepository getSpringRepository() {
		return this.loanTypeProfileSpringRepository;
	}

	public LoanTypeProfile findLoanTypeProfileByLoanTypeAndEffectiveDate(LoanType loanType, Date effectiveDate){
		return this.loanTypeProfileSpringRepository.findLoanTypeProfileByLoanTypeAndEffectiveDate(loanType.getLoanTypeID(), effectiveDate);
	}

	public List<LoanTypeProfile> findLoanTypeProfilesByLoanTypeAndEffectiveDate(LoanType loanType, Date effectiveDate){
		return this.getSpringRepository().findLoanTypeProfilesByLoanTypeAndEffectiveDate(loanType.getLoanTypeID(), effectiveDate);
	}

	public List<LoanTypeProfile> findLoanTypeProfilesByLoanType(LoanType loanType){
		return this.getSpringRepository().findLoanTypeProfilesByLoanType(loanType.getLoanTypeID());
	}
}

@Repository
interface LoanTypeProfileSpringRepository extends BaseSpringRepository<LoanTypeProfile, Long>{

	@Query("select ltp from LoanTypeProfile ltp where ltp.loanType.loanTypeID = :loanTypeID and ltp.effectiveDate <= :effectiveDate " +
			"and (ltp.endDate is null or ltp.endDate > :effectiveDate)")
	public LoanTypeProfile findLoanTypeProfileByLoanTypeAndEffectiveDate(@Param("loanTypeID") Long loanTypeID, @Param("effectiveDate") Date effectiveDate);

	@Query("select ltp from LoanTypeProfile ltp where ltp.loanType.loanTypeID = :loanTypeID and (ltp.endDate is null or ltp.endDate > :effectiveDate)")
	public List<LoanTypeProfile> findLoanTypeProfilesByLoanTypeAndEffectiveDate(@Param("loanTypeID") Long loanTypeID, @Param("effectiveDate") Date effectiveDate);

	@Query("select ltp from LoanTypeProfile ltp where ltp.loanType.loanTypeID = :loanTypeID")
	public List<LoanTypeProfile> findLoanTypeProfilesByLoanType(@Param("loanTypeID") Long loanTypeID);
}
