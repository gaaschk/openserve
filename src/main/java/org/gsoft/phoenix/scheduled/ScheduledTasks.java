package org.gsoft.phoenix.scheduled;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.service.batch.BatchProcessingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTasks {
	@Resource
	private BatchProcessingService batchService;
	
	//Run every day at midnight
	@Scheduled(cron="0 0 0 * * ?")
	public void updateLoans(){
		List<Loan> loans = batchService.getAllActiveLoans();
		for(Loan loan:loans){
			batchService.runBatchProcesses(loan);
		}
	};
}
