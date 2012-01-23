package org.gsoft.phoenix.scheduled;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.service.batch.BatchProcessingService;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTasks {
	@Resource
	private ApplicationContext applicationContext;
	
	//Run every day at midnight
	@Scheduled(cron="0 0 0 * * ?")
	public void updateLoans(){
		BatchProcessingService batchService = this.getBatchService();
		List<Loan> loans = batchService.getAllActiveLoans();
		for(Loan loan:loans){
			batchService.runBatchProcesses(loan);
		}
	}
	
	/**
	 * Need to lookup objects here because this instance may be held indefinitely by the 
	 * scheduler.  We want a new instance of the batchProcessingservice per session, 
	 * so we don't want the reference to be stored as a class variable.
	 * @return
	 */
	private BatchProcessingService getBatchService(){
		return applicationContext.getBean(BatchProcessingService.class);
	}
}
