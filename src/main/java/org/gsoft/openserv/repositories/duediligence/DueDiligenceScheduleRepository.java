package org.gsoft.openserv.repositories.duediligence;

import java.util.List;

import org.gsoft.openserv.domain.duediligence.DueDiligenceSchedule;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DueDiligenceScheduleRepository extends BaseRepository<DueDiligenceSchedule, Long>{
	@Query("select dds from DueDiligenceSchedule dds where dds.loanProgram.loanProgramID = :loanProgramId")
	public List<DueDiligenceSchedule> findAllByLoanProgramId(@Param("loanProgramId") Long loanProgramId);
}