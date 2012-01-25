package org.gsoft.phoenix.web.controller.addloan;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.service.PersonService;
import org.gsoft.phoenix.service.loanentry.LoanEntryService;
import org.gsoft.phoenix.web.controller.addloan.model.DisbursementModel;
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
		loanModel.setNewDisbursement(new DisbursementModel());
		loanModel.setPerson(newPersonModel);
		return loanModel;
	}

	public void addDisbursement(LoanEntryModel loanModel){
		if(loanModel.getAddedDisbursements() == null)
			loanModel.setAddedDisbursements(new ArrayList<DisbursementModel>());
		loanModel.getAddedDisbursements().add(loanModel.getNewDisbursement());
		int startingPrincipal = (loanModel.getStartingPrincipal()==null)?0:loanModel.getStartingPrincipal();
		loanModel.setStartingPrincipal(startingPrincipal + loanModel.getNewDisbursement().getDisbursementAmount());
		loanModel.setNewDisbursement(new DisbursementModel());
	}
	
	public Long saveLoan(LoanEntryModel loanModel){
		Loan newLoanDoc = loanModelConverter.convertFromModel(loanModel);
		Loan loan = loanEntryService.addNewLoan(newLoanDoc,loanModel.getEffectiveDate());
		return loan.getLoanID();
	}
}
