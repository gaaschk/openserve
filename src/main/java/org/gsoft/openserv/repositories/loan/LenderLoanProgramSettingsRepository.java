package org.gsoft.openserv.repositories.loan;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LenderLoanProgramSettings;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class LenderLoanProgramSettingsRepository extends BaseRepository<LenderLoanProgramSettings, Long>{
	@Resource
	private LenderLoanProgramSpringRepository springRepository;

	@Override
	protected LenderLoanProgramSpringRepository getSpringRepository() {
		return springRepository;
	}
	
	public List<LenderLoanProgramSettings> findAllByLenderID(Long lenderID){
		return this.getSpringRepository().findAllByLenderID(lenderID);
	}

	public LenderLoanProgramSettings findByLoanProgramAndEffectiveDate(Long lenderID, LoanProgram loanProgram, Date effectiveDate){
		return this.getSpringRepository().findByLoanProgramAndEffectiveDate(lenderID, loanProgram.getLoanProgramID(), effectiveDate);
	}

	public List<LenderLoanProgramSettings> findAllByLoanProgramAndEffectiveDate(Long lenderID, LoanProgram loanProgram, Date effectiveDate){
		return this.getSpringRepository().findAllByLoanProgramAndEffectiveDate(lenderID, loanProgram.getLoanProgramID(), effectiveDate);
	}
}

interface LenderLoanProgramSpringRepository extends BaseSpringRepository<LenderLoanProgramSettings, Long>{
	@Query("SELECT llp FROM LenderLoanProgramSettings llp WHERE llp.lenderID = :lenderID")
	public List<LenderLoanProgramSettings> findAllByLenderID(@Param("lenderID") Long lenderID);	

	@Query("select llp FROM LenderLoanProgramSettings llp WHERE (llp.lenderID = :lenderID " +
			"AND llp.loanProgram.loanProgramID = :loanProgramID) AND (llp.programBeginDate >= :effectiveDate or llp.programBeginDate >= " +
			"(select max(llp2.programBeginDate) FROM LenderLoanProgramSettings llp2 WHERE llp2.lenderID = :lenderID " +
			"AND llp2.loanProgram.loanProgramID = :loanProgramID AND llp2.programBeginDate <= :effectiveDate))")
	public List<LenderLoanProgramSettings> findAllByLoanProgramAndEffectiveDate(@Param("lenderID") Long lenderID, @Param("loanProgramID") Long loanProgramID, @Param("effectiveDate") Date effectiveDate);
	
	@Query("select llp from LenderLoanProgramSettings llp where llp.lenderID = :lenderID " +
			"AND llp.loanProgram.loanProgramID = :loanProgramID and llp.programBeginDate <= :effectiveDate " +
			"and llp.programBeginDate >= (select max(llp2.programBeginDate) from LenderLoanProgramSettings llp2 where llp2.lenderID = :lenderID " +
			"AND llp2.loanProgram.loanProgramID = :loanProgramID and llp2.programBeginDate <= :effectiveDate) ")
	public LenderLoanProgramSettings findByLoanProgramAndEffectiveDate(@Param("lenderID") Long lenderID, @Param("loanProgramID") Long loanProgramID, @Param("effectiveDate") Date effectiveDate);
}
