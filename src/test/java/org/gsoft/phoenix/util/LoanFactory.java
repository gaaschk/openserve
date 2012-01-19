package org.gsoft.phoenix.util;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanType;
import org.springframework.stereotype.Repository;


@Repository
public class LoanFactory {
	@Resource
	private PersonFactory personFactory;
	
	public Loan getLoanByID(int testLoanID){
		Loan newLoan = new Loan();
		newLoan.setLoanType(LoanType.MORTGAGE);
		newLoan.setStartingFees(0);
		newLoan.setStartingInterest(new BigDecimal(0));
		newLoan.setStartingPrincipal(1000000);
		
		newLoan.setBorrower(personFactory.getPersonByID(1));
		
		return newLoan;
	}
}
