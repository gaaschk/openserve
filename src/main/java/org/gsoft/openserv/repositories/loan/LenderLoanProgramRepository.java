package org.gsoft.openserv.repositories.loan;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LenderLoanProgram;
import org.gsoft.openserv.domain.loan.LoanType;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class LenderLoanProgramRepository extends BaseRepository<LenderLoanProgram, Long>{
	@Resource
	private LenderLoanProgramSpringRepository springRepository;

	@Override
	protected LenderLoanProgramSpringRepository getSpringRepository() {
		return springRepository;
	}
	
	public List<LenderLoanProgram> findAllByLenderID(Long lenderID){
		return this.getSpringRepository().findAllByLenderID(lenderID);
	}

	public LenderLoanProgram findByLoanTypeAndEffectiveDate(Long lenderID, LoanType loanType, Date effectiveDate){
		return this.getSpringRepository().findByLoanTypeAndEffectiveDate(lenderID, loanType.getLoanTypeID(), effectiveDate);
	}

	public List<LenderLoanProgram> findAllByLoanTypeAndEffectiveDate(Long lenderID, LoanType loanType, Date effectiveDate){
		return this.getSpringRepository().findAllByLoanTypeAndEffectiveDate(lenderID, loanType.getLoanTypeID(), effectiveDate);
	}
}

interface LenderLoanProgramSpringRepository extends BaseSpringRepository<LenderLoanProgram, Long>{
	@Query("SELECT llp FROM LenderLoanProgram llp WHERE llp.lenderID = :lenderID")
	public List<LenderLoanProgram> findAllByLenderID(@Param("lenderID") Long lenderID);	

	@Query("select llp FROM LenderLoanProgram llp WHERE llp.lenderID = :lenderID " +
			"AND llp.loanType.loanTypeID = :loanTypeID AND (llp.programEndDate is null OR llp.programEndDate > :effectiveDate)")
	public List<LenderLoanProgram> findAllByLoanTypeAndEffectiveDate(@Param("lenderID") Long lenderID, @Param("loanTypeID") Long loanTypeID, @Param("effectiveDate") Date effectiveDate);
	
	@Query("select llp from LenderLoanProgram llp where llp.lenderID = :lenderID " +
			"AND llp.loanType.loanTypeID = :loanTypeID and llp.programBeginDate <= :effectiveDate " +
			"and (llp.programEndDate is null or llp.programEndDate > :effectiveDate)")
	public LenderLoanProgram findByLoanTypeAndEffectiveDate(@Param("lenderID") Long lenderID, @Param("loanTypeID") Long loanTypeID, @Param("effectiveDate") Date effectiveDate);
}
