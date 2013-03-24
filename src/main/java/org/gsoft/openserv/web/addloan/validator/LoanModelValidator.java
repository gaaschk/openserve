package org.gsoft.openserv.web.addloan.validator;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.web.addloan.model.DisbursementModel;
import org.gsoft.openserv.web.addloan.model.LoanEntryModel;
import org.gsoft.openserv.web.support.validators.BaseValidator;
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
