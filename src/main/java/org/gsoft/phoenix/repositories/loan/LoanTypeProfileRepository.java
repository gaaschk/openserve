package org.gsoft.phoenix.repositories.loan;

import java.util.Date;

import org.gsoft.phoenix.domain.loan.LoanType;
import org.gsoft.phoenix.domain.loan.LoanTypeProfile;
import org.gsoft.phoenix.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTypeProfileRepository extends BaseRepository<LoanTypeProfile, Long>{

	@Query("select ltp from LoanTypeProfile ltp where ltp.loanType = :loanType and ltp.effectiveDate <= :effectiveDate " +
			"and (ltp.endDate is null or ltp.endDate > effectiveDate)")
	public LoanTypeProfile findLoanTypeProfileByLoanTypeAndEffectiveDate(@Param("loanType") LoanType loanType, @Param("effectiveDate") Date effectiveDate);
}
