package org.gsoft.phoenix.repositories.amortization;

import org.gsoft.phoenix.domain.amortization.AmortizationSchedule;
import org.gsoft.phoenix.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmortizationScheduleRepository extends BaseRepository<AmortizationSchedule, Long> {

}
