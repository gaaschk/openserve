package org.gsoft.phoenix.buslogic.repayment;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Disbursement;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanTypeProfile;
import org.gsoft.phoenix.domain.loan.RepaymentStartType;
import org.gsoft.phoenix.repositories.loan.LoanTypeProfileRepository;
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
