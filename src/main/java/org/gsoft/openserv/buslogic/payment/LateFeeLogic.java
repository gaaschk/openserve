package org.gsoft.openserv.buslogic.payment;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.payment.LateFee;
import org.gsoft.openserv.domain.payment.billing.LoanStatementSummary;
import org.gsoft.openserv.domain.payment.billing.StatementPaySummary;
import org.gsoft.openserv.repositories.payment.LateFeeRepository;
import org.springframework.stereotype.Component;

@Component
public class LateFeeLogic {
	@Resource
	private SystemSettingsLogic systemSettings;
	@Resource
	private LateFeeRepository lateFeeRepository;
	
	public void updateLateFees(LoanStatementSummary loanStatementSummary){
		Date systemDate = systemSettings.getCurrentSystemDate();
		for(StatementPaySummary sps : loanStatementSummary.getStatements()){
			if(sps.shouldAssessLateFee(systemDate)){
				LateFee lateFee = lateFeeRepository.findByBillingStatementID(sps.getStatement().getBillingStatementID());
				if(lateFee == null){
					lateFee = new LateFee();
					lateFee.setBillingStatementID(sps.getStatement().getBillingStatementID());
					lateFee.setFeeAmount(sps.getEffectiveLoanProgramSettings().getLateFeeAmount());
					lateFee.setPostedDate(systemDate);
					lateFee.setEffectiveDate(sps.expectedLateFeeDate());
					lateFee.setCancelled(false);
					lateFeeRepository.save(lateFee);
				}
				else if(lateFee.getFeeAmount() != sps.getEffectiveLateFeeAmount()){
					lateFee.setFeeAmount(sps.getEffectiveLateFeeAmount());
				}
			}
			else{
				LateFee lateFee = lateFeeRepository.findByBillingStatementID(sps.getStatement().getBillingStatementID());
				if(lateFee != null){
					lateFee.setCancelled(true);
				}
			}
		}
	}
}
