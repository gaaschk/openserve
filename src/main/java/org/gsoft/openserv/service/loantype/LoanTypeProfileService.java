package org.gsoft.openserv.service.loantype;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanType;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.gsoft.openserv.repositories.loan.LoanTypeRepository;
import org.gsoft.openserv.rulesengine.annotation.RunRulesEngine;
import org.gsoft.openserv.rulesengine.event.LoanTypeProfileChangedEvent;
import org.gsoft.openserv.rulesengine.event.SystemEventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class LoanTypeProfileService {
	@Resource
	private LoanTypeRepository loanTypeRepository;
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	@Resource
	private SystemEventHandler systemEventHandler;
	
	@Transactional
	@RunRulesEngine
	public LoanTypeProfile saveLoanTypeProfile(LoanTypeProfile loanTypeProfile){
		loanTypeProfile = loanTypeProfileRepository.save(loanTypeProfile);
		systemEventHandler.handleEvent(new LoanTypeProfileChangedEvent(loanTypeProfile));
		return loanTypeProfile;
	}

	@Transactional
	@RunRulesEngine
	public LoanType saveLoanType(LoanType loanType){
		loanType = loanTypeRepository.save(loanType);
		return loanType;
	}
}
