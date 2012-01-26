package org.gsoft.phoenix.web.controller.addloan;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.service.PersonService;
import org.gsoft.phoenix.service.loanentry.LoanEntryService;
import org.gsoft.phoenix.web.models.DisbursementModel;
import org.gsoft.phoenix.web.models.LoanEntryModel;
import org.gsoft.phoenix.web.models.PersonModel;
import org.gsoft.phoenix.web.person.PersonSearchCriteria;
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
		Loan loan = loanEntryService.addNewLoan(newLoanDoc,loanModel.getEffectiveDate());
		return loan.getLoanID();
	}
}
