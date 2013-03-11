package org.gsoft.openserv.service.loantype;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.repositories.loan.DefaultLoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.loan.LoanTypeRepository;
import org.gsoft.openserv.rulesengine.annotation.RunRulesEngine;
import org.gsoft.openserv.rulesengine.event.LoanProgramChangedEvent;
import org.gsoft.openserv.rulesengine.event.DefaultLoanProgramSettingsChangedEvent;
import org.gsoft.openserv.rulesengine.event.SystemEventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class LoanTypeProfileService {
	@Resource
	private LoanTypeRepository loanTypeRepository;
	@Resource
	private DefaultLoanProgramSettingsRepository loanTypeProfileRepository;
	@Resource
	private SystemEventHandler systemEventHandler;
	
	@Transactional
	@RunRulesEngine
	public DefaultLoanProgramSettings saveLoanTypeProfile(DefaultLoanProgramSettings loanTypeProfile){
		if(loanTypeProfile.getDefaultLoanProgramSettingsID() != null){
			DefaultLoanProgramSettings oldLtp = loanTypeProfileRepository.findOne(loanTypeProfile.getDefaultLoanProgramSettingsID());
			if(!loanTypeProfile.equals(oldLtp)){
				loanTypeProfile = loanTypeProfileRepository.save(loanTypeProfile);
				systemEventHandler.handleEvent(new DefaultLoanProgramSettingsChangedEvent(loanTypeProfile));
			}
		}
		else{
			loanTypeProfile = loanTypeProfileRepository.save(loanTypeProfile);
			systemEventHandler.handleEvent(new DefaultLoanProgramSettingsChangedEvent(loanTypeProfile));
		}
		return loanTypeProfile;
	}

	@Transactional
	@RunRulesEngine
	public LoanProgram saveLoanType(LoanProgram loanType){
		if(loanType.getLoanProgramID() != null){
			LoanProgram oldLt = loanTypeRepository.findOne(loanType.getLoanProgramID());
			if(!loanType.equals(oldLt)){
				loanType = loanTypeRepository.save(loanType);
				systemEventHandler.handleEvent(new LoanProgramChangedEvent(loanType));
			}
		}
		else{
			loanType = loanTypeRepository.save(loanType);
			systemEventHandler.handleEvent(new LoanProgramChangedEvent(loanType));
		}
		return loanType;
	}
}
