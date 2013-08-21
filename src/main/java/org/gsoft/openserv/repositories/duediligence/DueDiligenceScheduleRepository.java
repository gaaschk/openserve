package org.gsoft.openserv.repositories.duediligence;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.duediligence.DueDiligenceSchedule;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class DueDiligenceScheduleRepository extends BaseRepository<DueDiligenceSchedule, Long>{
	@Resource
	private DueDiligenceScheduleSpringRepository springRepository;
	
	@Override
	protected DueDiligenceScheduleSpringRepository getSpringRepository() {
		return springRepository;
	}
	
	public List<DueDiligenceSchedule> findAllByLoanProgramId(Long loanProgramId){
		return this.getSpringRepository().findAllByLoanProgramId(loanProgramId);
	}
	
}

@Repository
interface DueDiligenceScheduleSpringRepository extends BaseSpringRepository<DueDiligenceSchedule, Long>{
	@Query("select dds from DueDiligenceSchedule dds where dds.loanProgram.loanProgramID = :loanProgramId")
	public List<DueDiligenceSchedule> findAllByLoanProgramId(@Param("loanProgramId") Long loanProgramId);
}
