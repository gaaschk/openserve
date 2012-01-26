package org.gsoft.phoenix.web.controller.addloan;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.system.SystemSettingsLogic;
import org.gsoft.phoenix.web.models.DisbursementModel;
import org.gsoft.phoenix.web.models.LoanEntryModel;
import org.gsoft.phoenix.web.validators.BaseValidator;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.stereotype.Component;

@Component
public class LoanModelValidator extends BaseValidator{
	@Resource
	private SystemSettingsLogic systemSettings;
	
	public void validateEnterDisbursements(LoanEntryModel loanModel, ValidationContext validationContext){
		if(systemSettings.getCurrentSystemDate().before(loanModel.getNewDisbursement().getDisbursementDate())){
			this.addMessage(validationContext, "newDisbursement.disbursementDate", "error.future.disbursement");
		}
	}
	
	public void validateEnterLoanDetails(LoanEntryModel loanModel, ValidationContext validationContext){
		if(systemSettings.getCurrentSystemDate().before(loanModel.getEffectiveDate())){
			this.addMessage(validationContext, "effectiveDate", "error.future.loan");
		}
		if(loanModel.getAddedDisbursements() == null || loanModel.getAddedDisbursements().size() <= 0){
			this.addMessage(validationContext, "addedDisbursements", "error.size.disbursements");
		}
		else{
			for(DisbursementModel disb:loanModel.getAddedDisbursements()){
				if(disb.getDisbursementDate().after(systemSettings.getCurrentSystemDate()))
					this.addMessage(validationContext, "addedDisbursements", "error.future.disbursement");
			}
		}
	}
}
