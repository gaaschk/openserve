package org.gsoft.openserv.repositories.loan;

import java.util.Date;
import java.util.List;

import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultLoanProgramSettingsRepository extends BaseRepository<DefaultLoanProgramSettings, Long>{

	@Query("select ltp from DefaultLoanProgramSettings ltp where ltp.loanProgram.loanProgramID = :loanProgramID and ltp.effectiveDate <= :effectiveDate " +
			"and ltp.effectiveDate >= (select max(ltp2.effectiveDate) from  DefaultLoanProgramSettings ltp2 where ltp2.loanProgram.loanProgramID = :loanProgramID and ltp2.effectiveDate <= :effectiveDate)")
	public DefaultLoanProgramSettings findDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(@Param("loanProgramID") Long loanProgramID, @Param("effectiveDate") Date effectiveDate);

	@Query("select ltp from DefaultLoanProgramSettings ltp where (ltp.loanProgram.loanProgramID = :loanProgramID and ltp.effectiveDate >= :effectiveDate) or ltp.effectiveDate >= (select max(ltp2.effectiveDate) from  DefaultLoanProgramSettings ltp2 where ltp2.loanProgram.loanProgramID = :loanProgramID and ltp2.effectiveDate <= :effectiveDate) order by ltp.effectiveDate DESC")
	public List<DefaultLoanProgramSettings> findAllDefaultLoanProgramSettingsByLoanProgramAndEffectiveDate(@Param("loanProgramID") Long loanProgramID, @Param("effectiveDate") Date effectiveDate);

	@Query("select ltp from DefaultLoanProgramSettings ltp where ltp.loanProgram.loanProgramID = :loanProgramID")
	public List<DefaultLoanProgramSettings> findAllDefaultLoanProgramSettingsByLoanProgram(@Param("loanProgramID") Long loanProgramID);
}