package org.gsoft.openserv.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Disbursement;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanType;
import org.springframework.stereotype.Repository;


@Repository
public class LoanFactory {
	@Resource
	private PersonFactory personFactory;
	
	public Loan getLoanByID(int testLoanID){
		Loan newLoan = new Loan();
		newLoan.setServicingStartDate(new Date());
		newLoan.setLoanType(LoanType.MORTGAGE);
		newLoan.setDisbursements(new ArrayList<Disbursement>());
		Disbursement disb = new Disbursement();
		disb.setLoan(newLoan);
		disb.setDisbursementEffectiveDate(new Date());
		disb.setDisbursementAmount(1000000);
		newLoan.getDisbursements().add(disb);
		newLoan.setStartingPrincipal(1000000);
		newLoan.setStartingFees(0);
		newLoan.setStartingInterest(new BigDecimal(0));
		newLoan.setBorrower(personFactory.getPersonByID(1));
		
		return newLoan;
	}
}
