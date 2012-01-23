package org.gsoft.phoenix.web.controller.addloan.model;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.loan.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanEntryModelConverter {
	@Resource
	private PersonModelConverter personModelConverter;
	
	public Loan convertFromModel(LoanEntryModel loanModel){
		Loan newLoan = new Loan();
		newLoan.setLoanType(loanModel.getLoanType());
		Person person = personModelConverter.convertFromModel(loanModel.getPerson());
		newLoan.setBorrower(person);
		newLoan.setStartingPrincipal(loanModel.getStartingPrincipal());
		newLoan.setStartingInterest(loanModel.getStartingInterest());
		newLoan.setStartingFees(loanModel.getStartingFees());
		return newLoan;
	}
}
