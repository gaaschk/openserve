package org.gsoft.phoenix.web.controller.addloan.model;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.service.domain.LoanEntryDocument;
import org.springframework.stereotype.Component;

@Component
public class LoanEntryModelConverter {
	@Resource
	private PersonModelConverter personModelConverter;
	
	public LoanEntryDocument convertFromModel(LoanEntryModel loanModel){
		LoanEntryDocument newLoanDoc = new LoanEntryDocument();
		newLoanDoc.setLoanType(loanModel.getLoanType());
		Person person = personModelConverter.convertFromModel(loanModel.getPerson());
		newLoanDoc.setBorrower(person);
		newLoanDoc.setStartingPrincipal(loanModel.getStartingPrincipal());
		newLoanDoc.setStartingInterest(loanModel.getStartingInterest());
		newLoanDoc.setStartingFees(loanModel.getStartingFees());
		return newLoanDoc;
	}
}
