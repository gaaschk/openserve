package org.gsoft.phoenix.util;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.loan.LoanType;
import org.gsoft.phoenix.service.loanentry.domain.NewLoanData;
import org.springframework.stereotype.Repository;


@Repository
public class LoanFactory {
	@Resource
	private PersonFactory personFactory;
	
	public NewLoanData getLoanByID(int testLoanID){
		NewLoanData newLoan = new NewLoanData();
		newLoan.setLoanType(LoanType.MORTGAGE);
		newLoan.setStartingFees(0);
		newLoan.setStartingInterest(new BigDecimal(0));
		newLoan.setStartingPrincipal(1000000);
		
		newLoan.setBorrower(personFactory.getPersonByID(1));
		
		return newLoan;
	}
}
