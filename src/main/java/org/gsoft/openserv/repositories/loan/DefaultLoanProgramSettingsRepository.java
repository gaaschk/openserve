package org.gsoft.openserv.repositories.loan;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultLoanProgramSettingsRepository extends BaseRepository<DefaultLoanProgramSettings, Long>{
	@Resource
	private DefaultLoanProgramSettingsSpringRepository defaultLoanProgramSettingsSpringRepository;

	@Override
	protected DefaultLoanProgramSettingsSpringRepository getSpringRepository() {
		return this.defaultLoanProgramSettingsSpringRepository;
	}

	public DefaultLoanProgramSettings findDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(LoanProgram loanProgram, Date effectiveDate){
		return this.defaultLoanProgramSettingsSpringRepository.findDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(loanProgram.getLoanProgramID(), effectiveDate);
	}

	public List<DefaultLoanProgramSettings> findAllDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(LoanProgram loanProgram, Date effectiveDate){
		return this.getSpringRepository().findAllDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(loanProgram.getLoanProgramID(), effectiveDate);
	}

	public List<DefaultLoanProgramSettings> findAllDefaultLoanProgramSettingsByLoanProgram(LoanProgram loanProgram){
		return this.getSpringRepository().findAllDefaultLoanProgramSettingsByLoanProgram(loanProgram.getLoanProgramID());
	}
}

@Repository
interface DefaultLoanProgramSettingsSpringRepository extends BaseSpringRepository<DefaultLoanProgramSettings, Long>{

	@Query("select ltp from DefaultLoanProgramSettings ltp where ltp.loanProgram.loanProgramID = :loanProgramID and ltp.effectiveDate <= :effectiveDate " +
			"and (ltp.endDate is null or ltp.endDate > :effectiveDate)")
	public DefaultLoanProgramSettings findDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(@Param("loanProgramID") Long loanProgramID, @Param("effectiveDate") Date effectiveDate);

	@Query("select ltp from DefaultLoanProgramSettings ltp where ltp.loanProgram.loanProgramID = :loanProgramID and (ltp.endDate is null or ltp.endDate > :effectiveDate)")
	public List<DefaultLoanProgramSettings> findAllDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(@Param("loanProgramID") Long loanProgramID, @Param("effectiveDate") Date effectiveDate);

	@Query("select ltp from DefaultLoanProgramSettings ltp where ltp.loanProgram.loanProgramID = :loanProgramID")
	public List<DefaultLoanProgramSettings> findAllDefaultLoanProgramSettingsByLoanProgram(@Param("loanProgramID") Long loanProgramID);
}
