package org.gsoft.phoenix.web.controller.addloan;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.service.PersonService;
import org.gsoft.phoenix.service.loanentry.LoanEntryService;
import org.gsoft.phoenix.web.controller.addloan.model.LoanEntryModel;
import org.gsoft.phoenix.web.controller.addloan.model.LoanEntryModelConverter;
import org.gsoft.phoenix.web.controller.addloan.model.PersonModel;
import org.gsoft.phoenix.web.controller.addloan.model.PersonModelConverter;
import org.gsoft.phoenix.web.person.PersonSearchCriteria;
import org.springframework.stereotype.Service;

@Service
public class AddLoanFlowController {
	@Resource
	private PersonService personService;
	@Resource
	private PersonModelConverter personModelConverter;
	@Resource
	private LoanEntryModelConverter loanModelConverter;
	@Resource
	private LoanEntryService loanEntryService;
	
	public LoanEntryModel findPerson(PersonSearchCriteria personSearchCriteria){
		Person person = personService.findPersonBySSN(personSearchCriteria.getSsn());
		PersonModel newPersonModel = personModelConverter.convertToModel(person);
		newPersonModel.setSsn(personSearchCriteria.getSsn());
		LoanEntryModel loanModel = new LoanEntryModel();
		loanModel.setPerson(newPersonModel);
		return loanModel;
	}

	public Long saveLoan(LoanEntryModel loanModel){
		Loan newLoanDoc = loanModelConverter.convertFromModel(loanModel);
		Loan loan = loanEntryService.addNewLoan(newLoanDoc);
		return loan.getLoanID();
	}
}
