package org.gsoft.openserv.buslogic.repayment;

import java.util.Date;

import org.gsoft.openserv.domain.loan.Disbursement;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.domain.loan.RepaymentStartType;
import org.joda.time.DateTime;

public class RepaymentStartDateCalculator {
	
	public static Date calculateRepaymentStartDate(Loan loan){
		Date repaymentStartDate = null;
		if(loan.getDisbursements() != null && loan.getDisbursements().size() > 0){
			LoanTypeProfile ltp = loan.getEffectiveLoanTypeProfile();
			RepaymentStartType repaymentStartType = ltp.getRepaymentStartType();
			Disbursement firstDisb = null;
			Disbursement lastDisb = null;
			for(Disbursement disb: loan.getDisbursements()){
				if(firstDisb == null || firstDisb.getDisbursementEffectiveDate().after(disb.getDisbursementEffectiveDate())){
					firstDisb = disb;
				}
				if(lastDisb == null || lastDisb.getDisbursementEffectiveDate().before(disb.getDisbursementEffectiveDate())){
					lastDisb = disb;
				}
			}
			if(repaymentStartType == RepaymentStartType.FIRST_DISBUREMENT){
				repaymentStartDate = new DateTime(firstDisb.getDisbursementEffectiveDate()).plusMonths(ltp.getGraceMonths()).toDate();
			}
			else if(repaymentStartType == RepaymentStartType.LAST_DISBURSEMENT){
				repaymentStartDate = new DateTime(lastDisb.getDisbursementEffectiveDate()).plusMonths(ltp.getGraceMonths()).toDate();
			}
		}
		return repaymentStartDate;
	}
}
