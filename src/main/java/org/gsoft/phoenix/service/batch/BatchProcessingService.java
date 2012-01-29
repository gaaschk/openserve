package org.gsoft.phoenix.service.batch;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.gsoft.phoenix.rulesengine.annotation.RunRulesEngine;
import org.gsoft.phoenix.util.ListUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class BatchProcessingService {
	@Resource
	private LoanRepository loanRepository;
	
	public List<Loan> getAllActiveLoans(){
		return ListUtility.addAll(new ArrayList<Loan>(), loanRepository.findAll());
	}
	
	@RunRulesEngine
	@Transactional
	public void runBatchProcesses(Long loanID){
		loanRepository.findOne(loanID);
	}
}
