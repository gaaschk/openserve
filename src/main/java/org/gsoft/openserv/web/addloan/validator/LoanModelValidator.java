package org.gsoft.openserv.web.addloan.validator;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.web.addloan.model.DisbursementModel;
import org.gsoft.openserv.web.addloan.model.LoanEntryModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoanModelValidator implements Validator{
	@Resource
	private SystemSettingsLogic systemSettings;
	
	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz){
		return LoanEntryModel.class.equals(clazz);
	}
	
	
	@Override
	public void validate(Object loanModel, Errors e){
		this.validateEnterLoanDetails((LoanEntryModel)loanModel, e);
	}
	
	public void validateEnterLoanDetails(LoanEntryModel loanModel, Errors e){
		if(systemSettings.getCurrentSystemDate().before(loanModel.getEffectiveDate())){
			e.rejectValue("effectiveDate", "error.future.loan");
		}
		if(loanModel.getAddedDisbursements() == null || loanModel.getAddedDisbursements().size() <= 0){
			e.rejectValue("addedDisbursements", "error.size.disbursements");
		}
		else{
			for(DisbursementModel disb:loanModel.getAddedDisbursements()){
				if(disb.getDisbursementDate().after(systemSettings.getCurrentSystemDate()))
					e.rejectValue("addedDisbursements", "error.future.disbursement");
			}
		}
	}
}
