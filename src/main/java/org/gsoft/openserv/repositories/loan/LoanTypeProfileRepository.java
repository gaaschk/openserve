package org.gsoft.openserv.repositories.loan;

import java.util.Date;

import org.gsoft.openserv.domain.loan.LoanType;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTypeProfileRepository extends BaseSpringRepository<LoanTypeProfile, Long>{

	@Query("select ltp from LoanTypeProfile ltp where ltp.loanType = :loanType and ltp.effectiveDate <= :effectiveDate " +
			"and (ltp.endDate is null or ltp.endDate > effectiveDate)")
	public LoanTypeProfile findLoanTypeProfileByLoanTypeAndEffectiveDate(@Param("loanType") LoanType loanType, @Param("effectiveDate") Date effectiveDate);
}
