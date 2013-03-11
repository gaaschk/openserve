package org.gsoft.openserv.web.controller.addloan;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.domain.lender.Lender;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.repositories.lender.LenderRepository;
import org.gsoft.openserv.repositories.loan.LoanTypeRepository;
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
	@Resource
	private LoanTypeRepository loanTypeRepository;
	@Resource
	private LenderRepository lenderRepository;
	
	public LoanEntryModel findPerson(PersonSearchCriteria personSearchCriteria){
		Person person = personService.findPersonBySSN(personSearchCriteria.getSsn());
		PersonModel newPersonModel = conversionService.convert(person, PersonModel.class);
		if(newPersonModel == null)newPersonModel = new PersonModel();
		newPersonModel.setSsn(personSearchCriteria.getSsn());
		LoanEntryModel loanModel = new LoanEntryModel();
		loanModel.setNewDisbursement(new DisbursementModel());
		List<LoanProgram> loanTypes = loanTypeRepository.findAll();
		List<Lender> lenders = lenderRepository.findAll();
		loanModel.setLenderList(lenders);
		loanModel.setLoanTypeList(loanTypes);
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
