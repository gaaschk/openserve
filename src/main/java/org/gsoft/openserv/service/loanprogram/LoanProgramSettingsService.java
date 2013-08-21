package org.gsoft.openserv.service.loanprogram;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.repositories.loan.DefaultLoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.loan.LoanProgramRepository;
import org.gsoft.openserv.rulesengine.annotation.RunRulesEngine;
import org.gsoft.openserv.rulesengine.event.DefaultLoanProgramSettingsChangedEvent;
import org.gsoft.openserv.rulesengine.event.LoanProgramChangedEvent;
import org.gsoft.openserv.rulesengine.event.SystemEventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class LoanProgramSettingsService {
	@Resource
	private LoanProgramRepository loanProgramRepository;
	@Resource
	private DefaultLoanProgramSettingsRepository defaultLoanProgramSettingsRepository;
	@Resource
	private SystemEventHandler systemEventHandler;
	
	@Transactional
	@RunRulesEngine
	public DefaultLoanProgramSettings saveDefaultLoanProgramSettings(DefaultLoanProgramSettings defaultLoanProgramSettings){
		if(defaultLoanProgramSettings.getDefaultLoanProgramSettingsID() != null){
			DefaultLoanProgramSettings oldLtp = defaultLoanProgramSettingsRepository.findOne(defaultLoanProgramSettings.getDefaultLoanProgramSettingsID());
			if(!defaultLoanProgramSettings.equals(oldLtp)){
				defaultLoanProgramSettings = defaultLoanProgramSettingsRepository.save(defaultLoanProgramSettings);
				systemEventHandler.handleEvent(new DefaultLoanProgramSettingsChangedEvent(defaultLoanProgramSettings));
			}
		}
		else{
			defaultLoanProgramSettings = defaultLoanProgramSettingsRepository.save(defaultLoanProgramSettings);
			systemEventHandler.handleEvent(new DefaultLoanProgramSettingsChangedEvent(defaultLoanProgramSettings));
		}
		return defaultLoanProgramSettings;
	}

	@Transactional
	@RunRulesEngine
	public LoanProgram saveLoanProgram(LoanProgram loanProgram){
		if(loanProgram.getLoanProgramID() != null){
			LoanProgram oldLt = loanProgramRepository.findOne(loanProgram.getLoanProgramID());
			if(!loanProgram.equals(oldLt)){
				loanProgram = loanProgramRepository.save(loanProgram);
				systemEventHandler.handleEvent(new LoanProgramChangedEvent(loanProgram));
			}
		}
		else{
			loanProgram = loanProgramRepository.save(loanProgram);
			systemEventHandler.handleEvent(new LoanProgramChangedEvent(loanProgram));
		}
		return loanProgram;
	}
}
