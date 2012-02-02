package org.gsoft.openserv.web.controller.addloan;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.service.PersonService;
import org.gsoft.openserv.service.loanentry.LoanEntryService;
import org.gsoft.openserv.web.models.DisbursementModel;
import org.gsoft.openserv.web.models.LoanEntryModel;
import org.gsoft.openserv.web.models.PersonModel;
import org.gsoft.openserv.web.person.PersonSearchCriteria;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class AddLoanFlowController {
	@Resource
	private PersonService personService;
	@Resource
	private ConversionService conversionService;
	@Resource
	private LoanEntryService loanEntryService;
	
	public LoanEntryModel findPerson(PersonSearchCriteria personSearchCriteria){
		Person person = personService.findPersonBySSN(personSearchCriteria.getSsn());
		PersonModel newPersonModel = conversionService.convert(person, PersonModel.class);
		if(newPersonModel == null)newPersonModel = new PersonModel();
		newPersonModel.setSsn(personSearchCriteria.getSsn());
		LoanEntryModel loanModel = new LoanEntryModel();
		loanModel.setNewDisbursement(new DisbursementModel());
		loanModel.setPerson(newPersonModel);
		return loanModel;
	}

	public void addDisbursement(LoanEntryModel loanModel){
		if(loanModel.getAddedDisbursements() == null)
			loanModel.setAddedDisbursements(new ArrayList<DisbursementModel>());
		loanModel.getAddedDisbursements().add(loanModel.getNewDisbursement());
		loanModel.setNewDisbursement(new DisbursementModel());
	}
	
	public Long saveLoan(LoanEntryModel loanModel){
		Loan newLoanDoc = conversionService.convert(loanModel, Loan.class);
		Loan loan = loanEntryService.addNewLoan(newLoanDoc);
		return loan.getLoanID();
	}
}
