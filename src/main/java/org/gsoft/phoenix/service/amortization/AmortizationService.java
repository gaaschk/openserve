package org.gsoft.phoenix.service.amortization;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.amortization.AmortizationLogic;
import org.gsoft.phoenix.domain.amortization.AmortizationSchedule;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class AmortizationService {
	@Resource
	private AmortizationLogic amortizationLogic;
	
	@PreAuthorize("hasRole('PERM_CreateAmortizationSchedule')")
	@Transactional
	public AmortizationSchedule calculateAmortizationSchedule(List<Long> loanIDs){
		return amortizationLogic.createAmortizationSchedule(loanIDs);
	}
}
