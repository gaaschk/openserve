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
		Loan newLoanDoc = new Loan();
		newLoanDoc.setLoanType(loanModel.getLoanType());
		Person person = personModelConverter.convertFromModel(loanModel.getPerson());
		newLoanDoc.setBorrower(person);
		newLoanDoc.setStartingPrincipal(loanModel.getStartingPrincipal());
		newLoanDoc.setStartingInterest(loanModel.getStartingInterest());
		newLoanDoc.setStartingFees(loanModel.getStartingFees());
		return newLoanDoc;
	}
}
