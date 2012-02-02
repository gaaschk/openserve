package org.gsoft.openserv.buslogic.repayment;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Disbursement;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.loan.RepaymentStartType;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class RepaymentStartDateCalculator {
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	
	public void updateRepaymentStartDate(Loan loan){
		Long ltpID = loan.getEffectiveLoanTypeProfileID();
		LoanTypeProfile ltp = loanTypeProfileRepository.findOne(ltpID);
		RepaymentStartType repaymentStartType = ltp.getRepaymentStartType();
		Disbursement firstDisb = null;
		Disbursement lastDisb = null;
		for(Disbursement disb: loan.getDisbursements()){
			if(firstDisb == null || firstDisb.getDisbursementEffectiveDate().after(disb.getDisbursementEffectiveDate()))
				firstDisb = disb;
			if(lastDisb == null || lastDisb.getDisbursementEffectiveDate().before(disb.getDisbursementEffectiveDate()))
				lastDisb = disb;
		}
		if(repaymentStartType == RepaymentStartType.FIRST_DISBUREMENT){
			loan.setRepaymentStartDate(new DateTime(firstDisb.getDisbursementEffectiveDate()).plusMonths(ltp.getGraceMonths()).toDate());
		}
		else if(repaymentStartType == RepaymentStartType.LAST_DISBURSEMENT){
			loan.setRepaymentStartDate(new DateTime(lastDisb.getDisbursementEffectiveDate()).plusMonths(ltp.getGraceMonths()).toDate());
		}
	}
}
