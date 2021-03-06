package org.gsoft.openserv.service.batch;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.gsoft.openserv.rulesengine.annotation.RunRulesEngine;
import org.gsoft.openserv.rulesengine.event.LoanTemporalEvent;
import org.gsoft.openserv.rulesengine.event.SystemEventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class BatchProcessingService {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private SystemEventHandler systemEventHandler;
	
	public List<Loan> getAllActiveLoans(){
		return loanRepository.findAll();
	}
	
	@RunRulesEngine
	@Transactional
	public void runBatchProcesses(Long loanID){
		Loan loan = loanRepository.findOne(loanID);
		systemEventHandler.handleEvent(new LoanTemporalEvent(loan));
	}
}
